package grvt.cloud.epam.models;

import java.util.List;

public class Triangle {
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

    private Integer a;
    private Integer b;
    private Integer c;

    public Triangle(List<Integer> sides) {
        this.a = sides.get(0);
        this.b = sides.get(1);
        this.c = sides.get(2);
    }
}
