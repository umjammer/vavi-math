/*
 * https://introcs.cs.princeton.edu/java/99crypto/PollardRho.java.html
 */

package vavi.math.factor.generator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


/**
 * Compilation: javac PollardRho.java Execution: java PollardRho N
 *
 * Factor N using the Pollard-Rho method.
 *
 * % java PollardRho 44343535354351600000003434353 149 329569479697
 * 903019357561501
 */
public class PollardRho {

    /** */
    private final static BigInteger TWO = new BigInteger("2");

    /** */
    private final static SecureRandom random = new SecureRandom();

    static final Map<BigInteger, Boolean> cache = new HashMap<>();

    /** */
    private BigInteger rho(BigInteger N) {
        BigInteger divisor;
        BigInteger c = new BigInteger(N.bitLength(), random);
        BigInteger x = new BigInteger(N.bitLength(), random);
        BigInteger xx = x;

        // check divisibility by 2
        if (N.mod(TWO).compareTo(BigInteger.ZERO) == 0) {
            return TWO;
        }

        do {
            x = x.multiply(x).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            divisor = x.subtract(xx).gcd(N);
        } while ((divisor.compareTo(BigInteger.ONE)) == 0);

        return divisor;
    }

    /**
     * TODO slow (FactorUtilTest#test2 92%)
     */
    public void factor(BigInteger n) {
        if (n.compareTo(BigInteger.ONE) == 0) {
            return;
        }
//System.err.println(n);
        boolean p;
        if (cache.containsKey(n)) {
            p = cache.get(n);
        } else {
            p = n.isProbablePrime(20); // TODO slow (FactorUtilTest#test2 7%)
        }
        if (p) {
            c.accept(n);
            return;
        }
        BigInteger divisor = rho(n);
        factor(divisor);
        factor(n.divide(divisor));
    }

    Consumer<BigInteger> c;

    /** */
    public PollardRho(Consumer<BigInteger> c) {
        this.c = c;
    }
}
