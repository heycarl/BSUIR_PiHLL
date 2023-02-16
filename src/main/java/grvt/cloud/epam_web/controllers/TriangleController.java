package grvt.cloud.epam_web.controllers;

import grvt.cloud.epam_web.cache.TriangleCacheResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import grvt.cloud.epam_web.exceptions.IllegalArgumentsException;
import grvt.cloud.epam_web.models.Triangle;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


// Сервис должен принимать три параметра (сторона А, сторона Б, сторона В) и
// вернуть свойства треугольника (является ли треугольник равносторонним, равнобедренным, прямоугольным)
// для предоставленных параметров.

@RestController
@RequestMapping(
        value = "/api/v0/triangle_checker",
        produces = "application/json"
)
public class TriangleController {
    private static final Logger logger = LogManager.getLogger(TriangleController.class);
    private TriangleCacheResolver cache;
    @Autowired
    public void setTriangleCacheResolver(TriangleCacheResolver cache) {
        this.cache = cache;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String greeting(@RequestParam(name = "A") Integer a_side,
                           @RequestParam(name = "B") Integer b_side,
                           @RequestParam(name = "C") Integer c_side) throws IllegalArgumentsException {
        logger.info("GET /triangle_checker");
        Integer[] sides = {a_side, b_side, c_side};
        if (cache.containsValue(Arrays.hashCode(sides))) {
            logger.info("Returned from cache");
            return cache.getValue(Arrays.hashCode(sides));
        }

        Triangle triangle = new Triangle(a_side, b_side, c_side);
        triangle.validateTriangle();

        JSONObject response = new JSONObject();
        response.put("equilateral", triangle.checkEquilateral()); // равносторонний
        response.put("isosceles", triangle.checkIsosceles()); // ранвобедренный
        response.put("rectangular", triangle.checkRectangular()); // прямоугольный
        return cache.putValue(Arrays.hashCode(sides), response.toString());
    }
}
