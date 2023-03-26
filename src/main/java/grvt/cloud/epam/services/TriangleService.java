package grvt.cloud.epam.services;

import grvt.cloud.epam.exceptions.IllegalArgumentsException;
import grvt.cloud.epam.models.Triangle;

import java.util.List;
import java.util.Objects;

import static java.lang.Math.pow;

public class TriangleService {
    private final Triangle triangle;

    public TriangleService(List<Integer> sides) {
        this.triangle = new Triangle(sides);
    }

    public void setSides(Integer a, Integer b, Integer c) {
        triangle.setA(a);
        triangle.setB(b);
        triangle.setC(c);
    }

    public boolean checkEquilateral() {
        // Fake time-consuming operation
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return Objects.equals(triangle.getA(), triangle.getB())
                && Objects.equals(triangle.getB(), triangle.getC());
    }

    public boolean checkIsosceles() {
        return Objects.equals(triangle.getA(), triangle.getB()) ||
                Objects.equals(triangle.getB(), triangle.getC()) ||
                Objects.equals(triangle.getA(), triangle.getC());
    }

    @FunctionalInterface
    interface MathOperation {
        double calc(double a);
    }

    public boolean checkRectangular() {
        MathOperation sq;
        sq = (x) -> pow(x, 2);

        return sq.calc(triangle.getA()) == sq.calc(triangle.getB()) + sq.calc(triangle.getC()) ||
                sq.calc(triangle.getB()) == sq.calc(triangle.getA()) + sq.calc(triangle.getC()) ||
                sq.calc(triangle.getC()) == sq.calc(triangle.getA()) + sq.calc(triangle.getB());
    }

    public static boolean checkInvalidSide(int side) {
        return side <= 0;
    }

    public void validateTriangle() throws IllegalArgumentsException {
        if (checkInvalidSide(triangle.getA()) || checkInvalidSide(triangle.getB()) || checkInvalidSide(triangle.getC()))
            throw new IllegalArgumentsException();
    }

}
