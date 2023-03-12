package grvt.cloud.epam_web.controllers;

import grvt.cloud.epam_web.cache.TriangleCacheResolver;
import grvt.cloud.epam_web.perfomance_counter.Counter;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import grvt.cloud.epam_web.exceptions.IllegalArgumentsException;
import grvt.cloud.epam_web.models.Triangle;

import java.util.List;


// Сервис должен принимать три параметра (сторона А, сторона Б, сторона В) и
// вернуть свойства треугольника (является ли треугольник равносторонним, равнобедренным, прямоугольным)
// для предоставленных параметров.
@RestController
@Tag(name = "Triangle", description = "Triangle's options methods")
public class TriangleController {
    private static final Logger logger = LogManager.getLogger(TriangleController.class);
    private TriangleCacheResolver cache;
    @Autowired
    public void setTriangleCacheResolver(TriangleCacheResolver cache) {
        this.cache = cache;
    }
    private final RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
    private final StatefulRedisConnection<String, String> connection = redisClient.connect();

    @RequestMapping(value = "/api/v0/triangle/get_options",
                    method = RequestMethod.GET,
                    produces = "application/json"
    )
    @Operation(summary = "Options", description = "equilateral, isosceles and rectangular")
    public ResponseEntity<String> TriangleOptionsEndpoint(
            @RequestBody @Parameter(description = "Sides stream") List<Integer> requestSides
    ) throws IllegalArgumentsException {
        logger.info("GET /api/v0/triangle/get_options");
        Counter visitor = new Counter();
        visitor.start();

        if ((long) requestSides.size() != 3 || !requestSides.stream().allMatch(Triangle::validateSide))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Integer> sides = requestSides.stream()
                .sorted().toList();

        int hashCode = sides.hashCode();
        if (cache.containsValue(hashCode)) {
            logger.info("Returned from cache");
            return new ResponseEntity<>(cache.getValue(hashCode), HttpStatus.OK);
        }
        RedisCommands<String, String> syncCommands = connection.sync();
        String resp = syncCommands.get(String.valueOf(hashCode));
        if (resp != null) {
            logger.info("Returned from DB");
            return new ResponseEntity<>(cache.putValue(hashCode, resp), HttpStatus.OK);
        }

        Triangle triangle = new Triangle(sides);

        JSONObject response = new JSONObject();
        response.put("equilateral", triangle.checkEquilateral()); // равносторонний
        response.put("isosceles", triangle.checkIsosceles()); // ранвобедренный
        response.put("rectangular", triangle.checkRectangular()); // прямоугольный
        syncCommands.set(String.valueOf(hashCode), response.toString());
        return new ResponseEntity<>(cache.putValue(hashCode, response.toString()), HttpStatus.OK);
    }
}
