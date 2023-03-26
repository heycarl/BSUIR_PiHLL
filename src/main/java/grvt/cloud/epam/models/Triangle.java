package grvt.cloud.epam.models;

import grvt.cloud.epam.exceptions.IllegalArgumentsException;

import java.util.List;
import java.util.Objects;

import static java.lang.Math.pow;

public class Triangle {
    private Integer a;
    private Integer b;
    private Integer c;

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    public Triangle(List<Integer> sides) {
        this.a = sides.get(0);
        this.b = sides.get(1);
        this.c = sides.get(2);
    }

    public void save(Integer a, Integer b, Integer c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    public static boolean validateSide(int side) {
        return side > 0;
    }
    public void validateTriangle() throws IllegalArgumentsException {
        if (!validateSide(a) || !validateSide(b) || !validateSide(c))
            throw new IllegalArgumentsException();
    }

    public boolean checkEquilateral() {
        return Objects.equals(a, b) && Objects.equals(b, c);
    }

    public boolean checkIsosceles() {
        return Objects.equals(a, b) || Objects.equals(b, c) || Objects.equals(a, c);
    }

    @FunctionalInterface
    interface MathOperation {
        double calc(double a);
    }

    public boolean checkRectangular() {
        MathOperation sq;
        sq = (x) -> pow(x, 2);

        return sq.calc(a) == sq.calc(b) + sq.calc(c) ||
                sq.calc(b) == sq.calc(a) + sq.calc(c) ||
                sq.calc(c) == sq.calc(a) + sq.calc(b);
    }

}
