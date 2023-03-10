package grvt.cloud.epam_web.controllers;

import grvt.cloud.epam_web.exceptions.IllegalArgumentsException;
import grvt.cloud.epam_web.models.Triangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class TriangleControllerTest {
    static Triangle triangle = new Triangle(Arrays.asList(0,0,0));
    @Test
    void validateTriangle_exception() {
        Assertions.assertThrows(IllegalArgumentsException.class, () -> {
            triangle.Save(-1,3,1);
            triangle.validateTriangle();
        });
    }

    @Test
    void checkEquilateral() {
        triangle.Save(1,1,1);
        Assertions.assertTrue(triangle.checkEquilateral());

        triangle.Save(1,2,1);
        Assertions.assertFalse(triangle.checkEquilateral());
    }

    @Test
    void checkIsosceles() {
        triangle.Save(1,2,2);
        Assertions.assertTrue(triangle.checkIsosceles());

        triangle.Save(2,2,1);
        Assertions.assertTrue(triangle.checkIsosceles());

        triangle.Save(2,1,2);
        Assertions.assertTrue(triangle.checkIsosceles());

        triangle.Save(1,2,3);
        Assertions.assertFalse(triangle.checkIsosceles());
    }

    @Test
    void checkRectangular() {
        triangle.Save(3,4,5);
        Assertions.assertTrue(triangle.checkRectangular());

        triangle.Save(6,8,10);
        Assertions.assertTrue(triangle.checkRectangular());

        triangle.Save(1,28,10);
        Assertions.assertFalse(triangle.checkRectangular());
    }
}