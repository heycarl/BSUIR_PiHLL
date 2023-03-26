package grvt.cloud.epam.controllers;

import grvt.cloud.epam.cache.TriangleCacheResolver;
import grvt.cloud.epam.perfomance_counter.Counter;
import grvt.cloud.epam.services.TriangleService;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


// Сервис должен принимать три параметра (сторона А, сторона Б, сторона В) и
// вернуть свойства треугольника (является ли треугольник равносторонним, равнобедренным, прямоугольным)
// для предоставленных параметров.
@RestController
@Tag(name = "Triangle", description = "Triangle's options methods")
public class TriangleController {
    private static final Logger logger = LogManager.getLogger();
    private TriangleCacheResolver triangleCacheResolver;

    @Autowired
    public void setTriangleCacheResolver(TriangleCacheResolver cache) {
        this.triangleCacheResolver = cache;
    }

    private final RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
    private final StatefulRedisConnection<String, String> redisConnection = redisClient.connect();

    @RequestMapping(value = "/api/v0/triangle/getOptions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Options", description = "equilateral, isosceles and rectangular")
    public ResponseEntity<String> triangleOptionsEndpoint(
            @RequestBody @Parameter(description = "Triangle sides stream") List<Integer> requestSides
    ) {
        logger.info("GET /api/v0/triangle/getOptions");
        Counter visitor = new Counter();
        visitor.start();

        if ((long) requestSides.size() != 3 || requestSides.stream().anyMatch(TriangleService::checkInvalidSide))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Integer> sides = requestSides.stream()
                .sorted().toList();

        int hashCode = sides.hashCode();
        if (triangleCacheResolver.containsValue(hashCode)) {
            logger.info("Returned from cache");
            return new ResponseEntity<>(triangleCacheResolver.getValue(hashCode), HttpStatus.OK);
        }
        RedisCommands<String, String> syncCommands = redisConnection.sync();
        String resp = syncCommands.get(String.valueOf(hashCode));
        if (resp != null) {
            logger.info("Returned from DB");
            return new ResponseEntity<>(triangleCacheResolver.putValue(hashCode, resp), HttpStatus.OK);
        }

        TriangleService triangleService = new TriangleService(sides);

        CompletableFuture<String> futureCalculations = CompletableFuture.supplyAsync(() -> calculateOptions(triangleService));
        futureCalculations.thenAccept(result -> storeToDatabase(hashCode, result));

        return new ResponseEntity<>(new JSONObject().put("triangleHash", hashCode).toString(), HttpStatus.OK);
    }
    void storeToDatabase(int key, String value){
        RedisCommands<String, String> syncCommands = redisConnection.sync();
        syncCommands.set(String.valueOf(key), value);
    }
    String calculateOptions(TriangleService triangleService){
        JSONObject response = new JSONObject();
        response.put("equilateral", triangleService.checkEquilateral()); // равносторонний
        response.put("isosceles", triangleService.checkIsosceles());     // ранвобедренный
        response.put("rectangular", triangleService.checkRectangular()); // прямоугольный
        return response.toString();
    }

    @RequestMapping(value = "/api/v0/triangle/validateParams",
            method = RequestMethod.POST,
            produces = "application/json")
    @Operation(summary = "Bulk handling", description = "validates passed params")
    public ResponseEntity<?> triangleBulkEndpoint(
            @RequestBody @Parameter(description = "Sides stream") List<Integer> params
    ) {
        logger.info("POST /triangle_checker");
        if (params.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        JSONObject response = new JSONObject();
        response.put("argsProvided", (long) params.size());
        response.put("invalidArgs", params.stream().filter(TriangleService::checkInvalidSide).count());
        response.put("min", params.stream().min(Integer::compare).orElse(null));
        response.put("max", params.stream().max(Integer::compare).orElse(null));
        Map<Integer, Long> frequency = new HashMap<>();
        params.forEach(element -> frequency.put(element, params.stream()
                .filter(el -> Objects.equals(el, element))
                .count()));
        response.put("argsFrequency", new JSONObject(frequency));
        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }
}
