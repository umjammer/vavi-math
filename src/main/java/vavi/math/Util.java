/*
 * Copyright (c) 2013 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import vavi.math.factor.FactorGenerator;


/**
 * Util.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2013/02/26 umjammer initial version <br>
 */
public final class Util {

    private Util() {}

    /** use {@link BigInteger#gcd(BigInteger)} */
    @Deprecated
    public static final BigInteger gcd(BigInteger a, BigInteger b) {
        if (a.compareTo(b) < 0) {
            return gcd(b, a);
        } else {
            BigInteger m = a.remainder(b);
            if (m.compareTo(BigInteger.ZERO) == 0) {
                return b;
            } else {
                return gcd(b, m);
            }
        }
    }

    /** */
    public static final int gcd(int x, int y) {
        int wk = 1;
        x *= Integer.signum(x); // 符号無しにする
        y *= Integer.signum(y);
        while (y != 0) {
            wk = x % y;
            x = y;
            y = wk;
        }
        return x;
    }

    /** */
    public static BigInteger lcm(BigInteger l, BigInteger r) {
        return l.multiply(r).divide(l.gcd(r));
    }

    /** */
    public static BigInteger lcm(BigInteger l, BigInteger... rs) {
        BigInteger b = l;
        for (BigInteger r : rs) {
            b = lcm(b, r);
        }
        return b;
    }

    /** */
    public static BigInteger[] toBigIntegerArray(int[] elements) {
        BigInteger[] bia = new BigInteger[elements.length];
        for (int i = 0; i < elements.length; i++) {
            bia[i] = BigInteger.valueOf(elements[i]);
        }
        return bia;
    }

    /**
     * Compute factorial
     */
    public static BigInteger factorial(int n) {
//        if (n < 0) throw new IllegalArgumentException("negative number");
//        BigInteger fact = BigInteger.ONE;
//        for (int i = n; i > 1; i--) {
//            fact = fact.multiply(BigInteger.valueOf(i));
//        }
//        return fact;
        throw new UnsupportedOperationException();
    }

    /**
     * Compute factorial
     */
    public static BigInteger factorial(BigInteger n) {
        BigInteger f = BigInteger.ONE;
        while (n.compareTo(BigInteger.ONE) > 0) {
            f = f.multiply(n);
            n = n.subtract(BigInteger.ONE);
        }
        return f;
    }

    /**
     * 約数の個数
     * <p>
     * d(n) = (a<sub>1</sub> + 1)(a<sub>2</sub> + 1) ... (a<sub>m</sub> + 1)
     * @param indeces <i>a</i>
     */
    public static int countOfDivisors(Iterable<Integer> indeces) {
        int count = 1;
        for (int index : indeces) {
            count *= index + 1;
        }
        return count;
    }

    /**
     * 素因数分解
     * n = p<sub>1</sub><sup>a<sub>1</sub></sup>p<sub>2</sub><sup>a<sub>2</sub></sup> ... p<sub>m</sub><sup>a<sub>m</sub></sup>
     * <p>
     * @return Map&lt;<i>p</i>,<i>a</i>&gt;
     */
    public static Map<BigInteger, Integer> primeFactorization(BigInteger i) {
        Map<BigInteger, Integer> pairs = new HashMap<>();
        for (BigInteger f : new FactorGenerator(i)) {
            pairs.put(f, pairs.containsKey(f) ? pairs.get(f) + 1 : 1);
        }
        return pairs;
    }

    /**
     * list divisors
     * @return sorted
     */
    public static List<BigInteger> divisors(Map<BigInteger, Integer> entries) {
        return divisors(Arrays.asList(BigInteger.ONE), entries.entrySet().iterator());
    }

    /**
     * list divisors
     * @return sorted
     */
    private static List<BigInteger> divisors(List<BigInteger> preResults, Iterator<Map.Entry<BigInteger, Integer>> entries) {
        if (!entries.hasNext()) {
            Collections.sort(preResults);
            return preResults;
        }
        Map.Entry<BigInteger, Integer> e = entries.next();
        BigInteger p = e.getKey();
        int n = e.getValue();
        List<BigInteger> results = new ArrayList<>();
        for (BigInteger pp : preResults) {
            for (int a = 0; a <= n; a++) {
                results.add(pp.multiply(p.pow(a)));
            }
        }
        return divisors(results, entries);
    }

    /** sum of proper divisors of i */
    public static BigInteger sumOfProperDivisors(BigInteger i) {
        List<BigInteger> r = divisors(primeFactorization(i));
        BigInteger sum = i.negate();
        for (BigInteger x : r) {
            sum = sum.add(x);
        }
        return sum;
    }
}

/* */
