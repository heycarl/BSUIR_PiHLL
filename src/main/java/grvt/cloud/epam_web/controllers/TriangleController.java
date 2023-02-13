package grvt.cloud.epam_web.controllers;

import org.springframework.web.bind.annotation.*;

import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import grvt.cloud.epam_web.exceptions.*;

import java.util.Objects;

import static java.lang.Math.pow;

// Сервис должен принимать три параметра (сторона А, сторона Б, сторона В) и
// вернуть свойства треугольника (является ли треугольник равносторонним, равнобедренным, прямоугольным)
// для предоставленных параметров.

@RestController
@RequestMapping(
        value = "/api/v0/triangle_checker",
        produces = "application/json",
        method = RequestMethod.GET
)
public class TriangleController {
    private static final Logger logger = LogManager.getLogger(TriangleController.class);
    public String greeting(@RequestParam(name = "A") Integer a_side,
                           @RequestParam(name = "B") Integer b_side,
                           @RequestParam(name = "C") Integer c_side) throws IllegalArgumentsException {
        logger.info("GET /triangle_checker");

        validateTriangle(a_side, b_side, c_side);

        JSONObject response = new JSONObject();
        response.put("equilateral", checkEquilateral(a_side, b_side, c_side)); // равносторонний
        response.put("isosceles", checkIsosceles(a_side, b_side, c_side)); // ранвобедренный
        response.put("rectangular", checkRectangular(a_side, b_side, c_side)); // прямоугольный
        return response.toString();
    }
    public static void validateTriangle(Integer a, Integer b, Integer c) throws IllegalArgumentsException {
        if (a <= 0 || b <= 0 || c <= 0)
            throw new IllegalArgumentsException("Side can't be negative value");
    }

    public static boolean checkEquilateral(Integer a, Integer b, Integer c) {
        return Objects.equals(a, b) && Objects.equals(b, c);
    }

    public static boolean checkIsosceles(Integer a, Integer b, Integer c) {
        return Objects.equals(a, b) || Objects.equals(b, c) || Objects.equals(a, c);
    }

    @FunctionalInterface
    interface MathOperation {
        double calc(double a);
    }

    public static boolean checkRectangular(Integer a, Integer b, Integer c) {
        MathOperation sq;
        sq = (x) -> pow(x, 2);

        return sq.calc(a) == sq.calc(b) + sq.calc(c) ||
                sq.calc(b) == sq.calc(a) + sq.calc(c) ||
                sq.calc(c) == sq.calc(a) + sq.calc(b);
    }
}
