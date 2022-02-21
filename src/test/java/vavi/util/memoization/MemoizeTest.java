/*
 * Copyright (c) 2013 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util.memoization;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * MemoizeTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2013/03/05 umjammer initial version <br>
 */
@SuppressWarnings("unused")
public class MemoizeTest {

    static int count = 0;

    /** not work for static method */
    @Test
    public void test() {
        count = 0;
        int s = 0;
        s += m1(1);
        s += m1(1);
        assertEquals(2, count);
    }

    @Test
    public void test2() {
        count = 0;
        int s = 0;
        s += m2(1);
        s += m2(1);
        s += m2(1);
        s += m2(1);
        s += m2(1);
        s += m2(2);
        s += m2(2);
        s += m2(3);
        s += m2(3);
        s += m2(3);
        assertEquals(3, count);
    }

    @Test
    public void test3() {
        count = 0;
        int s = 0;
        s += m3(1);
        s += m3(1);
        s += m3(1);
        s += m3(1);
        s += m3(1);
        s += m3(2);
        s += m3(2);
        s += m3(3);
        s += m3(3);
        s += m3(3);
        assertEquals(3, count);
    }

    @Memoize(debug = true) // no mean
    static int m1(long i) {
        count++;
        return 0;
    }

    @Memoize(debug = true)
    int m2(int i) {
        count++;
        return 0;
    }

    @Memoize()
    int m3(int i) {
        count++;
        return 0;
    }

    @Memoize
    int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int fibForN = fib(n - 1) + fib(n - 2);

        return fibForN;
    }

    @Test
    void test4() {
        assertEquals(1556111435, fib(1000));
    }
}

/* */
