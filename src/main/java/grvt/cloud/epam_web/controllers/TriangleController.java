package grvt.cloud.epam_web.controllers;

import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

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
    private Integer aSide;
    private Integer bSide;
    private Integer cSide;

    @RequestMapping(method = RequestMethod.GET)
    public String greeting(@RequestParam(name = "A") Integer a_side,
                           @RequestParam(name = "B") Integer b_side,
                           @RequestParam(name = "C") Integer c_side) {
        aSide = a_side;
        bSide = b_side;
        cSide = c_side;
        JSONObject response = new JSONObject();
        response.put("equilateral", check_equilateral()); // равносторонний
        response.put("isosceles", check_isosceles()); // ранвобедренный
        response.put("rectangular", check_rectangular()); // прямоугольный
        return response.toString();
    }

    private boolean check_equilateral() {
        return Objects.equals(aSide, bSide) && Objects.equals(bSide, cSide);
    }

    private boolean check_isosceles() {
        return Objects.equals(aSide, bSide) || Objects.equals(bSide, cSide) || Objects.equals(aSide, cSide);
    }

    interface MathOperation {
        double calc(double a);
    }

    private boolean check_rectangular() {
        MathOperation sq;
        sq = (x) -> pow(x, 2);

        return sq.calc(aSide) == sq.calc(bSide) + sq.calc(cSide) ||
                sq.calc(bSide) == sq.calc(aSide) + sq.calc(cSide) ||
                sq.calc(cSide) == sq.calc(aSide) + sq.calc(bSide);
    }

}
