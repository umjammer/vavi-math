/*
 * Copyright (c) 2013 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math.prime;

import java.math.BigInteger;


/**
 * Util. 
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2013/02/28 umjammer initial version <br>
 */
public abstract class Util {

    /** */
    private static final BigInteger TWO = BigInteger.valueOf(2);

    /** Fermat's little theorem */
    public static boolean isPrime(BigInteger q) {
        q = q.abs();
        if (q.equals(TWO)) { return true; }
        if (q.compareTo(TWO) == -1 || q.and(BigInteger.ONE).equals(BigInteger.ZERO)) { return false; }
        return TWO.modPow(q.subtract(BigInteger.ONE), q).equals(BigInteger.ONE);
    }
}

/* */
