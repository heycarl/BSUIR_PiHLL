package grvt.cloud.epam.controllers;

import grvt.cloud.epam.exceptions.IllegalArgumentsException;
import grvt.cloud.epam.models.Triangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class TriangleControllerTest {
     static final Triangle triangle = new Triangle(Arrays.asList(0, 0, 0));
    @Test
    void validateTriangle_exception() {
        Assertions.assertThrows(IllegalArgumentsException.class, () -> {
            triangle.setSides(-1, 3, 1);
            triangle.validateTriangle();
        });
    }

    @Test
    void checkEquilateral() {
        triangle.setSides(1, 1, 1);
        Assertions.assertTrue(triangle.checkEquilateral());

        triangle.setSides(1, 2, 1);
        Assertions.assertFalse(triangle.checkEquilateral());
    }

    @Test
    void checkIsosceles() {
        triangle.setSides(1, 2, 2);
        Assertions.assertTrue(triangle.checkIsosceles());

        triangle.setSides(2, 2, 1);
        Assertions.assertTrue(triangle.checkIsosceles());

        triangle.setSides(2, 1, 2);
        Assertions.assertTrue(triangle.checkIsosceles());

        triangle.setSides(1, 2, 3);
        Assertions.assertFalse(triangle.checkIsosceles());
    }

    @Test
    void checkRectangular() {
        triangle.setSides(3, 4, 5);
        Assertions.assertTrue(triangle.checkRectangular());

        triangle.setSides(6, 8, 10);
        Assertions.assertTrue(triangle.checkRectangular());

        triangle.setSides(1, 28, 10);
        Assertions.assertFalse(triangle.checkRectangular());
    }
}