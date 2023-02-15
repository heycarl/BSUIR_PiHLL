package grvt.cloud.epam_web.models;

import grvt.cloud.epam_web.exceptions.IllegalArgumentsException;

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

    public Triangle(Integer a, Integer b, Integer c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void Save(Integer a, Integer b, Integer c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void validateTriangle() throws IllegalArgumentsException {
        if (a <= 0 || b <= 0 || c <= 0)
            throw new IllegalArgumentsException("Side can't be negative value");
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
