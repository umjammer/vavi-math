/*
 * https://stackoverflow.com/q/4643647/6102938
 */

package vavi.math.factor.generator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import vavi.math.Util;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;


/**
 * factorization.
 *
 * TODO it takes about 5~7 seconds (pythos3 returns immediately).
 * TODO BigInteger#gcd, modPow uses mostly of time
 *
 * java9 has {@link BigInteger#TWO}, {@link BigInteger#sqrt()}
 */
public class Factorizor {

    /** */
    private final static SecureRandom random = new SecureRandom();

    private final static BigInteger TWO = new BigInteger("2");
    private final static BigInteger THREE = new BigInteger("3");
    private final static BigInteger FOUR = new BigInteger("4");
    private final static BigInteger SIX = new BigInteger("6");

    // http://stackoverflow.com/questions/2068372/fastest-way-to-list-all-primes-below-n-in-python/3035188#3035188
    // Input n >= 6, Returns a list of primes, 2 <= p < n
    private static Set<BigInteger> primesBelow(BigInteger n) {
        boolean correction = n.mod(SIX).compareTo(ONE) > 0;
        n = new BigInteger[] { n, n.subtract(ONE), n.add(FOUR), n.add(THREE), n.add(TWO), n.add(ONE) }[n.mod(SIX).intValue()];
        boolean[] sieve = new boolean[Util.floorDiv(n, THREE).intValue()];
        for (int i = 1; i < sieve.length; i++) { sieve[i] = true; }
        sieve[0] = false;
        for (int i = 0; i < Math.floorDiv(Util.sqrt(n).intValue(), 3) + 1; i++) {
            if (sieve[i]) {
                int k = (3 * i + 1) | 1;
//System.err.println("1: [" + (k * k / 3) + "::" + (2 * k) + "] = " + (Math.floorDiv((n.divide(SIX).intValue() - Math.floorDiv(k * k, 6) - 1), k) + 1) + ", " + k + ", " + n.divide(SIX).intValue() + ", " + Math.floorDiv((k * k), 6));
                for (int l = 0; l < Math.floorDiv((n.divide(SIX).intValue() - Math.floorDiv(k * k, 6) - 1), k) + 1; l++) {
                    sieve[Math.floorDiv(k * k, 3) + l * (2 * k)] = false;
                }
//System.err.println("2: [" + Math.floorDiv(k * k + 4 * k - 2 * k * (i % 2), 3) + "::" + (2 * k) + "] = " + (Math.floorDiv(n.divide(SIX).intValue() - Math.floorDiv(k * k + 4 * k - 2 * k * (i % 2), 6) - 1, k) + 1));
                for (int l = 0; l < Math.floorDiv(n.divide(SIX).intValue() - Math.floorDiv(k * k + 4 * k - 2 * k * (i % 2), 6) - 1, k) + 1; l++) {
                    sieve[Math.floorDiv(k * k + 4 * k - 2 * k * (i % 2), 3) + l * (2 * k)] = false;
                }
            }
        }
        Set<BigInteger> r = new HashSet<>();
        r.add(TWO);
        r.add(THREE);
//System.err.println("range: " + Util.floorDiv(n, THREE).subtract(correction ? ONE : ZERO));
//for (int i = 1; i < Util.floorDiv(n, THREE).subtract(correction ? ONE : ZERO).intValue(); i++) { System.err.printf("{%d: '%s'}, ", i, sieve[i] ? "T" : "F"); } System.err.println();
        for (BigInteger i = ONE; i.compareTo(Util.floorDiv(n, THREE).subtract(correction ? ONE : ZERO)) < 0; i = i.add(ONE)) {
//System.err.println("i: " + i + ", " + i.compareTo(N.divide(THREE).subtract(correction ? ONE : ZERO)));
            if (sieve[i.intValue()]) {
                r.add(THREE.multiply(i).add(ONE).or(ONE));
            }
        }
        return r;
    }

    private static final BigInteger OHT = BigInteger.valueOf(100000);
    private static final Set<BigInteger> smallPrimeSet = primesBelow(OHT);

    // http://en.wikipedia.org/wiki/Miller-Rabin_primality_test#Algorithm_and_running_time
    private static boolean isPrime(BigInteger n, int precision/*=7*/) {
        if (n.compareTo(ONE) < 0) {
            throw new IllegalArgumentException("Out of bounds, first argument must be > 0");
        } else if (n.compareTo(THREE) <= 0) {
            return n.compareTo(TWO) >= 0;
        } else if (n.mod(TWO) == ZERO) {
            return false;
        } else if (n.compareTo(OHT) < 0) {
            return smallPrimeSet.contains(n);
        }

        BigInteger d = n.subtract(ONE);
        BigInteger s = ZERO;
        while (d.mod(TWO) == ZERO) {
            d = Util.floorDiv(d, TWO);
            s = s.add(ONE);
        }

        for (int repeat = 0; repeat < precision; repeat++) {
            BigInteger a = new BigInteger(n.bitLength(), random); // TODO 2, n - 2;
            BigInteger x = a.modPow(d, n);

            if (x == ONE || x == n.subtract(ONE)) { continue; }

            for (BigInteger r = ZERO; r.compareTo(s.subtract(ONE)) < 0; r = r.add(ONE)) {
                x = x.modPow(TWO, n);
                if (x == ONE) { return false; }
                if (x == n.subtract(ONE)) { break; }
            }
            return false;
        }

        return true;
    }

    // https://comeoncodeon.wordpress.com/2010/09/18/pollard-rho-brent-integer-factorization/
    private static BigInteger pollardBrent(BigInteger n) {
        if (n.mod(TWO) == ZERO) { return TWO; }
        if (n.mod(THREE) == ZERO) { return THREE; }

        BigInteger y = new BigInteger(n.bitLength(), random);
        BigInteger c = new BigInteger(n.bitLength(), random); // TODO 1, n - 1
        BigInteger m = new BigInteger(n.bitLength(), random);
        BigInteger g = ONE, r = ONE, q = ONE;
        BigInteger x = null;
        BigInteger ys = null;
        while (g == ONE) {
            x = y;
            for (BigInteger i = ZERO; i.compareTo(r) < 0; i = i.add(ONE)) {
                y = y.modPow(TWO, n).add(c).mod(n);
            }
            BigInteger k = ZERO;
            while (k.compareTo(r) < 0 && g == ONE) {
                ys = y;
                for (BigInteger i = ZERO; i.compareTo(m.min(r.subtract(k))) < 0; i = i.add(ONE)) {
                    y = y.modPow(TWO, n).add(c).mod(n);
                    q = q.multiply(x.subtract(y).abs()).mod(n);
                }
                g = q.gcd(n);
                k = k.add(m);
            }
            r = r.multiply(TWO);
        }
        if (g == n) {
            while (true) {
                ys = ys.modPow(TWO, n).add(c).mod(n);
                g = x.subtract(ys).abs().gcd(n);
                if (g.compareTo(ONE) > 0) {
                    break;
                }
            }
        }

        return g;
    }

    // might seem low, but 1000*1000 = 1000000, so this will fully factor every composite < 1000000
    private static final Set<BigInteger> smallPrimes = primesBelow(BigInteger.valueOf(1000));

    public static Collection<BigInteger> primeFactors(BigInteger n) {
        Collection<BigInteger> factors = new ArrayList<>();

        for (BigInteger checker : smallPrimes) {
            while (n.mod(checker) == ZERO) {
                factors.add(checker);
                n = Util.floorDiv(n, checker);
            }
            if (checker.compareTo(n) > 0) { break; }
        }

        if (n.compareTo(TWO) < 0) { return factors; }

        while (n.compareTo(ONE) > 0) {
            if (isPrime(n, 7)) {
                factors.add(n);
                break;
            }
            //  trial division did not fully factor, switch to pollard-brent
            BigInteger factor = pollardBrent(n);
            //  recurse to factor the not necessarily prime factor returned by pollard-brent
            factors.addAll(primeFactors(factor));
            n = Util.floorDiv(n, factor);
        }

        return factors;
    }

    public static Map<BigInteger, Integer> factorization(BigInteger n) {
        Map<BigInteger, Integer> factors = new HashMap<>();
        for (BigInteger p1 : primeFactors(n)) {
            if (factors.containsKey(p1)) {
                factors.put(p1, factors.get(p1) + 1);
            } else {
                factors.put(p1, 1);
            }
        }
        return factors;
    }

    private static Map<BigInteger, BigInteger> totients = new HashMap<>();

    public static BigInteger totient(BigInteger n) {
        if (n == ZERO) { return ONE; }

        if (totients.containsKey(n)) {
            return totients.get(n);
        }

        BigInteger tot = ONE;
        for (Map.Entry<BigInteger, Integer> e : factorization(n).entrySet()) {
            BigInteger p = e.getKey();
            int exp = e.getValue();
            tot = tot.multiply(p.subtract(ONE)).multiply(p.pow(exp - 1));
        }
        totients.put(n, tot);
        return tot;
    }

    public static void main(String[] args) throws Exception {
        for (int i = 10; i < 100; i++) {
            System.err.println(i + ": " + primesBelow(BigInteger.valueOf(i)).toString());
        }
    }
}

/* */