/*
 * Copyright (c) 2013 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * RationalTest. 
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2013/02/06 umjammer initial version <br>
 */
public class RationalTest {

    @Test
    public void test() throws Exception {
        Rational r1 = new Rational(1, 3);

        assertEquals(new Rational(1), r1.multiply(3));
        assertEquals(new Rational(0), r1.subtract(new Rational(1, 3)));
        assertEquals(new Rational(1), r1.add(new Rational(2, 3)));
        assertEquals(new Rational(1, 6), r1.divide(2));

        assertEquals(new Rational(6, 3), new Rational(60, 30));
    }

    @Test
    public void イコールメソッドのテスト() throws Exception {
        assertEquals(new Rational(100), new Rational(100));
    }
}

/* */
