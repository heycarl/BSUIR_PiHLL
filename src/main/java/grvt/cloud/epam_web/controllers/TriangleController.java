package grvt.cloud.epam_web.controllers;

import org.springframework.web.bind.annotation.*;

import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import grvt.cloud.epam_web.exceptions.IllegalArgumentsException;
import grvt.cloud.epam_web.models.Triangle;


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

    @RequestMapping(method = RequestMethod.GET)
    public String greeting(@RequestParam(name = "A") Integer a_side,
                           @RequestParam(name = "B") Integer b_side,
                           @RequestParam(name = "C") Integer c_side) throws IllegalArgumentsException {
        logger.info("GET /triangle_checker");

        Triangle triangle = new Triangle(a_side, b_side, c_side);
        triangle.validateTriangle();

        JSONObject response = new JSONObject();
        response.put("equilateral", triangle.checkEquilateral()); // равносторонний
        response.put("isosceles", triangle.checkIsosceles()); // ранвобедренный
        response.put("rectangular", triangle.checkRectangular()); // прямоугольный
        return response.toString();
    }
}
