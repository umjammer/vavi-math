/*
 * https://stackoverflow.com/q/4643647/6102938
 */

package vavi.math.factor.generator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;


/**
 * factorization.
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

    // TODO too slow use prepared table
    // http://stackoverflow.com/questions/2068372/fastest-way-to-list-all-primes-below-n-in-python/3035188#3035188
    // Input N>=6, Returns a list of primes, 2 <= p < N
    private Set<BigInteger> primesbelow(BigInteger N) {
        boolean correction = N.mod(SIX).compareTo(ONE) > 0;
        N = new BigInteger[] { N, N.subtract(ONE), N.add(FOUR), N.add(THREE), N.add(TWO), N.add(ONE) }[N.mod(SIX).intValue()];
        boolean[] sieve = new boolean[N.divide(THREE).intValue()];
        for (int i = 1; i < sieve.length; i++) { sieve[i] = true; }
        sieve[0] = false;
        for (int i = 0; i < sqrt(N).intValue() / 3 + 1; i++) {
            if (sieve[i]) {
                int k = (3 * i + 1) | 1;
                for (int j = k * k / 3; j < sieve.length; j += 2 * k) {
                    for (int l = 0; l < (N.divide(SIX).intValue() - (k * k) / 6 - 1) / k + 1; l++) {
                        if (j + l < sieve.length) {
                            sieve[j + l] = false;
                        }
                    }
                }
                for (int j = (k * k + 4 * k - 2 * k * (i % 2)) / 3; j < sieve.length; j += 2 * k) {
                    for (int l = 0; l < (N.divide(SIX).intValue() - (k * k + 4 * k - 2 * k * (i % 2)) / 6 - 1) / k + 1; l++) {
                        if (j + l < sieve.length) {
                            sieve[j + l] = false;
                        }
                    }
                }
            }
        }
        Set<BigInteger> r = new HashSet<>();
        r.add(TWO);
        r.add(THREE);
        for (BigInteger i = ONE; i.compareTo(N.divide(THREE).subtract(correction ? ONE : ZERO)) < 0; i.add(ONE)) {
            if (sieve[i.intValue()]) {
                r.add(THREE.multiply(i).add(ONE).or(ONE));
            }
        }
        return r;
    }

    private static final BigInteger _smallprimeset = new BigInteger("100000");
    private final Set<BigInteger> smallprimeset = primesbelow(_smallprimeset);

    // http://en.wikipedia.org/wiki/Miller-Rabin_primality_test#Algorithm_and_running_time
    private boolean isprime(BigInteger n, int precision/*=7*/) {
        if (n.compareTo(ONE) < 0) {
            throw new IllegalArgumentException("Out of bounds, first argument must be > 0");
        } else if (n.compareTo(THREE) <= 0) {
            return n.compareTo(TWO) >= 0;
        } else if (n.mod(TWO) == ZERO) {
            return false;
        } else if (n.compareTo(_smallprimeset) < 0) {
            return smallprimeset.contains(n);
        }

        BigInteger d = n.subtract(ONE);
        BigInteger s = ZERO;
        while (d.mod(TWO) == ZERO) {
            d = d.divide(TWO);
            s = s.add(ONE);
        }

        for (int repeat = 0; repeat < precision; repeat++) {
            BigInteger a = new BigInteger(n.bitLength(), random); // TODO 2, n - 2;
            BigInteger x = a.modPow(d, n);

            if (x == ONE || x == n.subtract(ONE)) { continue; }

            for (BigInteger r = ZERO; r.compareTo(s.subtract(ONE)) < 0; r.add(ONE)) {
                x = x.modPow(TWO, n);
                if (x == ONE) { return false; }
                if (x == n.subtract(ONE)) { break; }
            }
            return false;
        }

        return true;
    }

    // https://comeoncodeon.wordpress.com/2010/09/18/pollard-rho-brent-integer-factorization/
    private BigInteger pollard_brent(BigInteger n) {
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
            for (BigInteger i = ZERO; i.compareTo(r) < 0; i.add(ONE)) {
                y = y.modPow(TWO, n).add(c).mod(n);
            }
            BigInteger k = ZERO;
            while (k.compareTo(r) < 0 && g == ONE) {
                ys = y;
                for (BigInteger i = ZERO; i.compareTo(m.min(r.subtract(k))) < 0; i.add(ONE)) {
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
    private final Set<BigInteger> smallprimes = primesbelow(new BigInteger("1000"));

    public Set<BigInteger> primefactors(BigInteger n, boolean sort/*=false*/) {
        Set<BigInteger> factors = new HashSet<>();

        for (BigInteger checker : smallprimes) {
            while (n.mod(checker) == ZERO) {
                factors.add(checker);
                n = n.divide(checker);
            }
            if (checker.compareTo(n) > 0) { break; }
        }

        if (n.compareTo(TWO) < 0) { return factors; }

        while (n.compareTo(ONE) > 0) {
            if (isprime(n, 7)) {
                factors.add(n);
                break;
            }
            //  trial division did not fully factor, switch to pollard-brent
            BigInteger factor = pollard_brent(n);
            //  recurse to factor the not necessarily prime factor returned by pollard-brent
            factors.addAll(primefactors(factor, sort));
            n = n.divide(factor);
        }

        if (sort) { new TreeSet<>(factors); }

        return factors;
    }

    public Map<BigInteger, Integer> factorization(BigInteger n) {
        Map<BigInteger, Integer> factors = new HashMap<>();
        for (BigInteger p1 : primefactors(n, false)) {
            if (factors.containsKey(p1)) {
                factors.put(p1, factors.get(p1) + 1);
            } else {
                factors.put(p1, 1);
            }
        }
        return factors;
    }

    private Map<BigInteger, BigInteger> totients = new HashMap<>();

    private BigInteger totient(BigInteger n) {
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

//    BigInteger gcd(BigInteger a, BigInteger b) {
//        if (a == b) { return a; }
//        while (b.compareTo(ZERO) > 0) { a = b; b = a.mod(b); }
//        return a;
//    }

//    private BigInteger lcm(BigInteger a, BigInteger b) {
//        return a.divide(a.gcd(b)).multiply(b).abs();
//    }

    private static BigInteger sqrt(BigInteger val) {
        BigInteger half = BigInteger.ZERO.setBit(val.bitLength() / 2);
        BigInteger cur = half;

        while (true) {
            BigInteger tmp = half.add(val.divide(half)).shiftRight(1);

            if (tmp.equals(half) || tmp.equals(cur))
                return tmp;

            cur = half;
            half = tmp;
        }
    }
}

/* */