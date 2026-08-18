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

    /**
     * Miller-Rabin Primality Test
     * @see "https://chatgpt.com/c/6737a4e6-d968-8005-a982-9af81975c70f"
     */
    public static boolean isPrime(BigInteger n, int k) {
        // Step 1: Handle simple cases
        if (n.compareTo(BigInteger.ONE) <= 0) return false; // 0 and 1 are not primes
        if (n.equals(BigInteger.TWO) || n.equals(BigInteger.valueOf(3))) return true;
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) return false; // even numbers > 2 are not prime

        // Step 2: Write n as (2^r) * d + 1
        BigInteger d = n.subtract(BigInteger.ONE);
        int r = 0;
        while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            d = d.divide(BigInteger.TWO);
            r++;
        }

        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < k; i++) {
            // Step 3: Pick a random integer a in [2, n - 2]
            BigInteger a = BigInteger.valueOf(2).add(BigInteger.valueOf(Math.abs(rand.nextInt())).mod(n.subtract(BigInteger.valueOf(4))));
            if (!millerRabinPass(a, d, n, r)) {
                return false;
            }
        }
        return true;
    }

    // Helper function for Miller-Rabin test
    private static boolean millerRabinPass(BigInteger a, BigInteger d, BigInteger n, int r) {
        // Step 4: Compute a^d % n
        BigInteger x = a.modPow(d, n);
        if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) return true;

        // Step 5: Repeat r-1 times: x = x^2 % n
        for (int i = 0; i < r - 1; i++) {
            x = x.modPow(BigInteger.TWO, n);
            if (x.equals(n.subtract(BigInteger.ONE))) return true;
        }
        return false;
    }

    Consumer<BigInteger> c;

    /** */
    public PollardRho(Consumer<BigInteger> c) {
        this.c = c;
    }
}
