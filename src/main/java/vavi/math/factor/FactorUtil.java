/*
 * Copyright (c) 2013 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math.factor;

import java.math.BigInteger;
import java.util.Iterator;

import vavi.math.factor.generator.PollardRho;
import vavi.math.factor.generator.PollardRhoGenerator;
import vavi.util.Generator;


/**
 * FactorUtil.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2013/02/12 umjammer initial version <br>
 */
public abstract class FactorUtil {

    public static BigInteger maxFactorByGenerator(BigInteger n) {
        Generator<BigInteger> g = new PollardRhoGenerator(n);
        Iterator<BigInteger> i = g.iterator();
        BigInteger bi = BigInteger.ZERO;
        while (i.hasNext()) {
            BigInteger r = i.next();
            bi = r.compareTo(bi) > 0 ? r : bi;
        }
        return bi;
    }

    public static BigInteger maxFactor(BigInteger n) {
        BigInteger[] bi = { BigInteger.ZERO };
        PollardRho g = new PollardRho(r -> bi[0] = r.compareTo(bi[0]) > 0 ? r : bi[0]);
        g.factor(n);
        return bi[0];
    }
}

/* */
