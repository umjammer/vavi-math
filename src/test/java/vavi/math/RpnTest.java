/*
 * Copyright (c) 2012 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * RpnTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2012/11/01 umjammer initial version <br>
 */
public class RpnTest {

    @Test
    public void test() throws Exception {
        Rpn rpn = new Rpn();

        System.err.println(rpn.doubleValue("1 1 +"));
        System.err.println(rpn.rationalValue("1 3 /"));

        assertEquals(3, rpn.doubleValue("1 2 +"), 0.001);
    }

    @Test
    public void test2() throws Exception {
        Rpn rpn = new Rpn();
        assertEquals(new Rational(1, 3), rpn.rationalValue("1 3 /"));
    }

    @Test
    public void test3() throws Exception {
        String infix = Rpn.toInfix("1 3 + 3 /");
        System.err.println(infix);
        assertEquals("(1 + 3) / 3", infix);
    }

    @DisplayName("test pattern")
    @Test
    public void test4() throws Exception {
        long n = System.nanoTime();
        char[][] result = Rpn.generatePattern(9, 10);
        System.err.println("--- 9, 10 ---, " + result.length + ", " + (System.nanoTime() - n));
//        for (char[] str : result) {
//            for (char c : str) {
//                System.err.print(c);
//            }
//            System.err.println();
//        }
        assertEquals(4862, result.length);

        n = System.nanoTime();
        result = Rpn.generatePattern(3, 4);
        System.err.println("--- 3, 4 ---, " + result.length + ", " + (System.nanoTime() - n));
//        for (char[] str : result) {
//            for (char c : str) {
//                System.err.print(c);
//            }
//            System.err.println();
//        }
        assertEquals(5, result.length);
    }
}

/* */
