package grvt.cloud.epam.controllers;

import grvt.cloud.epam.exceptions.IllegalArgumentsException;
import grvt.cloud.epam.services.TriangleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class TriangleControllerTest {
    static final TriangleService triangleService = new TriangleService(Arrays.asList(0, 0, 0));
    @Test
    void validateTriangle_exception() {
        Assertions.assertThrows(IllegalArgumentsException.class, () -> {
            triangleService.setSides(-1, 3, 1);
            triangleService.validateTriangle();
        });
    }

    @Test
    void checkEquilateral() {
        triangleService.setSides(1, 1, 1);
        Assertions.assertTrue(triangleService.checkEquilateral());

        triangleService.setSides(1, 2, 1);
        Assertions.assertFalse(triangleService.checkEquilateral());
    }

    @Test
    void checkIsosceles() {
        triangleService.setSides(1, 2, 2);
        Assertions.assertTrue(triangleService.checkIsosceles());

        triangleService.setSides(2, 2, 1);
        Assertions.assertTrue(triangleService.checkIsosceles());

        triangleService.setSides(2, 1, 2);
        Assertions.assertTrue(triangleService.checkIsosceles());

        triangleService.setSides(1, 2, 3);
        Assertions.assertFalse(triangleService.checkIsosceles());
    }

    @Test
    void checkRectangular() {
        triangleService.setSides(3, 4, 5);
        Assertions.assertTrue(triangleService.checkRectangular());

        triangleService.setSides(6, 8, 10);
        Assertions.assertTrue(triangleService.checkRectangular());

        triangleService.setSides(1, 28, 10);
        Assertions.assertFalse(triangleService.checkRectangular());
    }
}