package grvt.cloud.epam_web.controllers;

import grvt.cloud.epam_web.exceptions.IllegalArgumentsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TriangleControllerTest {
    @Test
    void validateTriangle_exception() {
        Assertions.assertThrows(IllegalArgumentsException.class, () -> {
            TriangleController.validateTriangle(1,-1,0);
        });
    }

    @Test
    void checkEquilateral() {
        Assertions.assertTrue(TriangleController.checkEquilateral(1,1,1));
        Assertions.assertFalse(TriangleController.checkEquilateral(1,2,1));
    }

    @Test
    void checkIsosceles() {
        Assertions.assertTrue(TriangleController.checkIsosceles(1,2,2));
        Assertions.assertTrue(TriangleController.checkIsosceles(2,2,1));
        Assertions.assertTrue(TriangleController.checkIsosceles(2,1,2));
        Assertions.assertFalse(TriangleController.checkIsosceles(1,2,3));
    }

    @Test
    void checkRectangular() {
        Assertions.assertTrue(TriangleController.checkRectangular(3,4,5));
        Assertions.assertTrue(TriangleController.checkRectangular(6,8,10));
        Assertions.assertFalse(TriangleController.checkRectangular(1,28,10));
    }
}