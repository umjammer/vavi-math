/*
 * http://www.cs.princeton.edu/introcs/78crypto/PollardRho.java.html
 */

package vavi.math.factor.generator;

import java.math.BigInteger;
import java.security.SecureRandom;

import vavi.util.Generator;


/**
 * Compilation: javac PollardRho.java Execution: java PollardRho N
 *
 * Factor N using the Pollard-Rho method.
 *
 * % java PollardRho 44343535354351600000003434353 149 329569479697
 * 903019357561501
 */
public class PollardRhoGenerator extends Generator<BigInteger> {

    /** */
    private final static BigInteger TWO = new BigInteger("2");

    /** */
    private final static SecureRandom random = new SecureRandom();

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

    /** */
    private void factor(BigInteger n) {
        if (n.compareTo(BigInteger.ONE) == 0) {
            return;
        }
        if (n.isProbablePrime(20)) {
            yield(n);
            return;
        }
        BigInteger divisor = rho(n);
        factor(divisor);
        factor(n.divide(divisor));
        return;
    }

    /** */
    private BigInteger n;

    /** */
    public PollardRhoGenerator(BigInteger n) {
        this.n = n;
    }

    @Override
    public void run() {
        factor(n);
    }
}
