package org.jing.core.lang;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-01-31 <br>
 */
public class Pair2 <A, B> {
    private A a;

    private B b;

    public Pair2(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public Pair2() {
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    @Override public String toString() {
        return new StringBuilder("<").append(a).append(", ").append(b).append(">").toString();
    }
}
