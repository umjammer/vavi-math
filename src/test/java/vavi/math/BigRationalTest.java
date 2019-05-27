/*
 * Copyright (c) 2013 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * RationalTest. 
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2013/02/06 umjammer initial version <br>
 */
public class BigRationalTest {

    @Test
    public void test() throws Exception {
        BigRational r1 = new BigRational(1, 3);

        assertEquals(new BigRational(1), r1.multiply(new BigRational(3)));
        assertEquals(new BigRational(0), r1.subtract(new BigRational(1, 3)));
        assertEquals(new BigRational(1), r1.add(new BigRational(2, 3)));
        assertEquals(new BigRational(1, 6), r1.divide(new BigRational(2)));
    }

    @Test
    public void イコールメソッドのテスト() throws Exception {
        assertEquals(new BigRational(100), new BigRational(100));
    }
}

/* */
