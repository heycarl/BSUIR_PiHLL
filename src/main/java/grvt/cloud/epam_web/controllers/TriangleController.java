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
        produces = "application/json"
)
public class TriangleController {
    private static final Logger logger = LogManager.getLogger(TriangleController.class);
    private Integer aSide;
    private Integer bSide;
    private Integer cSide;

    @RequestMapping(method = RequestMethod.GET)
    public String greeting(@RequestParam(name = "A") Integer a_side,
                           @RequestParam(name = "B") Integer b_side,
                           @RequestParam(name = "C") Integer c_side) throws IllegalArgumentsException {
        aSide = a_side;
        bSide = b_side;
        cSide = c_side;

        logger.info("GET /triangle_checker");

        validateData();

        JSONObject response = new JSONObject();
        response.put("equilateral", checkEquilateral()); // равносторонний
        response.put("isosceles", checkIsosceles()); // ранвобедренный
        response.put("rectangular", checkRectangular()); // прямоугольный
        return response.toString();
    }
    public void validateData() throws IllegalArgumentsException {
        if (aSide <= 0 || bSide <= 0 || cSide <= 0)
            throw new IllegalArgumentsException("Side can't be negative value");
    }

    public boolean checkEquilateral() {
        return Objects.equals(aSide, bSide) && Objects.equals(bSide, cSide);
    }

    public boolean checkIsosceles() {
        return Objects.equals(aSide, bSide) || Objects.equals(bSide, cSide) || Objects.equals(aSide, cSide);
    }

    interface MathOperation {
        double calc(double a);
    }

    public boolean checkRectangular() {
        MathOperation sq;
        sq = (x) -> pow(x, 2);

        return sq.calc(aSide) == sq.calc(bSide) + sq.calc(cSide) ||
                sq.calc(bSide) == sq.calc(aSide) + sq.calc(cSide) ||
                sq.calc(cSide) == sq.calc(aSide) + sq.calc(bSide);
    }
}
