package com.bscc.db.util;

/**
 * 对类
 */
public class PairUtil<T, U> {
    private T one;
    private U two;

    public PairUtil() {

    }

    public PairUtil(T one, U two) {
        this.one = one;
        this.two = two;
    }

    public T getOne() {
        return one;
    }

    public void setOne(T one) {
        this.one = one;
    }

    public U getTwo() {
        return two;
    }

    public void setTwo(U two) {
        this.two = two;
    }

    @Override
    public String toString() {
        return "PairUtil{" +
                "one=" + one +
                ", two=" + two +
                '}';
    }
}
