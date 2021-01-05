package org.jing.core.lang;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-01-05 <br>
 */
public class Pair3<A, B, C> {
    private A a;

    private B b;

    private C c;

    public Pair3(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Pair3() {
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

    public C getC() {
        return c;
    }

    public Pair3<A, B, C> setC(C c) {
        this.c = c;
        return this;
    }

    @Override public String toString() {
        return String.format("<%s,%s,%s>", a, b, c);
    }
}