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
import org.springframework.web.bind.annotation.*;

import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import grvt.cloud.epam_web.exceptions.IllegalArgumentsException;
import grvt.cloud.epam_web.models.Triangle;

import java.util.Arrays;


// Сервис должен принимать три параметра (сторона А, сторона Б, сторона В) и
// вернуть свойства треугольника (является ли треугольник равносторонним, равнобедренным, прямоугольным)
// для предоставленных параметров.

@RestController
@RequestMapping(
        value = "/api/v0/triangle_checker",
        produces = "application/json"
)
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

    @RequestMapping(method = RequestMethod.GET)
    @Operation(summary = "Options", description = "equilateral, isosceles and rectangular")
    public String TriangleOptionsEndpoint(@RequestParam(name = "a_side") @Parameter(description = "A side") Integer aSide,
                           @RequestParam(name = "b_side") @Parameter(description = "B side") Integer bSide,
                           @RequestParam(name = "c_side") @Parameter(description = "C side") Integer cSide) throws IllegalArgumentsException {
        logger.info("GET /triangle_checker");
        Counter visitor = new Counter();
        visitor.start();
        Integer[] sides = {aSide, bSide, cSide};
        Arrays.sort(sides);
        int hashCode = Arrays.hashCode(sides);
        if (cache.containsValue(hashCode)) {
            logger.info("Returned from cache");
            return cache.getValue(Arrays.hashCode(sides));
        }
        RedisCommands<String, String> syncCommands = connection.sync();
        String resp = syncCommands.get(String.valueOf(hashCode));
        if (resp != null) {
            logger.info("Returned from DB");
            return cache.putValue(hashCode, resp);
        }

        Triangle triangle = new Triangle(aSide, bSide, cSide);
        triangle.validateTriangle();

        JSONObject response = new JSONObject();
        response.put("equilateral", triangle.checkEquilateral()); // равносторонний
        response.put("isosceles", triangle.checkIsosceles()); // ранвобедренный
        response.put("rectangular", triangle.checkRectangular()); // прямоугольный
        syncCommands.set(String.valueOf(hashCode), response.toString());
        return cache.putValue(hashCode, response.toString());
    }
}
