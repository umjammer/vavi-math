/*
 * Copyright (c) 2012 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math.factor.generator;

import java.math.BigInteger;

import org.junit.Test;

import vavi.math.StringUtil;

import static org.junit.Assert.assertEquals;


/**
 * PollardRhoTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2012/11/02 umjammer initial version <br>
 */
public class PollardRhoGeneratorTest {

    static final String actual = "[149, 329569479697, 903019357561501]";

    @Test
    public void test() {
        assertEquals(actual, StringUtil.toSequence(new PollardRhoGenerator(new BigInteger("44343535354351600000003434353"))));
    }

    /** */
    public static void main(String[] args) {
        BigInteger n = new BigInteger(args[0]);
        long t = System.currentTimeMillis();
        System.err.println(StringUtil.toSequence(new PollardRhoGenerator(n)));
        System.err.printf("%s: in %d ms\n", n, System.currentTimeMillis() - t);
    }
}

/* */
