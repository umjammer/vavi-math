/*
 * Copyright (c) 2013 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
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

    /** */
    public static final BigInteger TWO = BigInteger.valueOf(2);

    private Util() {}

    /** use {@link BigInteger#gcd(BigInteger)} */
    @Deprecated
    public static BigInteger gcd(BigInteger a, BigInteger b) {
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
    public static int gcd(int x, int y) {
        int wk = 1;
        x *= Integer.signum(x); // make it unsigned
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

    // @see https://stackoverflow.com/q/4643647/6102938
    public static BigInteger gcd2(BigInteger a, BigInteger b) {
        if (a.equals(b)) {
            return a;
        }
        while (b.compareTo(BigInteger.ZERO) > 0) {
            a = b;
            b = a.mod(b);
        }
        return a;
    }

    // @see https://stackoverflow.com/q/4643647/6102938
    public static BigInteger lcm2(BigInteger a, BigInteger b) {
        return floorDiv(a, a.gcd(b)).multiply(b).abs();
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
    @Deprecated
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
     * Returns count of divisors.
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
     * Prime factorization.
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
        return divisors(Collections.singletonList(BigInteger.ONE), entries.entrySet().iterator());
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

    /**
     * jdk9 has this.
     */
    public static BigInteger sqrt(BigInteger val) {
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

    private static final double LOG_2 = Math.log(2.0);
    private static final double LOG_10 = Math.log(10.0);

    // numbers greater than 10^MAX_DIGITS_10 or e^MAX_DIGITS_E are considered ('too big') for floating point operations
    private static final int MAX_DIGITS_10 = 294;
    private static final int MAX_DIGITS_2 = 977; // ~ MAX_DIGITS_10 * LN(10)/LN(2)
    private static final int MAX_DIGITS_E = 677; // ~ MAX_DIGITS_10 * LN(10)

    /**
     * Computes the natural logarithm of a {@link BigInteger} 
     * <p>
     * Works for really big integers (practically unlimited), even when the argument 
     * falls outside the {@code double} range
     * <p>
     *
     * @param val Argument
     * @return Natural logarithm, as in {@link java.lang.Math#log(double)}<br>
     * {@code Nan} if argument is negative, {@code NEGATIVE_INFINITY} if zero.
     * @see "https://stackoverflow.com/questions/6827516/logarithm-for-biginteger"
     */
    public static double log(BigInteger val) {
        if (val.signum() < 1)
            return val.signum() < 0 ? Double.NaN : Double.NEGATIVE_INFINITY;
        int blex = val.bitLength() - MAX_DIGITS_2; // any value in 60..1023 works here
        if (blex > 0)
            val = val.shiftRight(blex);
        double res = Math.log(val.doubleValue());
        return blex > 0 ? res + blex * LOG_2 : res;
    }

    /**
     * Computes the natural logarithm of a {@link BigDecimal}
     * <p>
     * Works for really big (or really small) arguments, even outside the double range.
     *
     * @param val Argument
     * @return Natural logarithm, as in {@link java.lang.Math#log(double)}<br>
     * {@code Nan} if argument is negative, {@code NEGATIVE_INFINITY} if zero.
     * @see "https://stackoverflow.com/questions/6827516/logarithm-for-biginteger"
     */
    public static double log(BigDecimal val) {
        if (val.signum() < 1)
            return val.signum() < 0 ? Double.NaN : Double.NEGATIVE_INFINITY;
        int digits = val.precision() - val.scale();
        if (digits < MAX_DIGITS_10 && digits > -MAX_DIGITS_10)
            return Math.log(val.doubleValue());
        else
            return log(val.unscaledValue()) - val.scale() * LOG_10;
    }

    /**
     * Computes the exponential function, returning a {@link BigDecimal} (precision ~ 16).
     * <p>
     * Works for very big and very small exponents, even when the result 
     * falls outside the double range.
     *
     * @param exponent Any finite value (infinite or {@code Nan} throws {@code IllegalArgumentException})    
     * @return The value of {@code e} (base of the natural logarithms) raised to the given exponent, 
     * as in {@link java.lang.Math#exp(double)}
     * @see "https://stackoverflow.com/questions/6827516/logarithm-for-biginteger"
     */
    public static BigDecimal exp(double exponent) {
        if (!Double.isFinite(exponent))
            throw new IllegalArgumentException("Infinite not accepted: " + exponent);
        // e^b = e^(b2+c) = e^b2 2^t with e^c = 2^t 
        double bc = MAX_DIGITS_E;
        if (exponent < bc && exponent > -bc)
            return new BigDecimal(Math.exp(exponent), MathContext.DECIMAL64);
        boolean neg = false;
        if (exponent < 0) {
            neg = true;
            exponent = -exponent;
        }
        double b2 = bc;
        double c = exponent - bc;
        int t = (int) Math.ceil(c / LOG_10);
        c = t * LOG_10;
        b2 = exponent - c;
        if (neg) {
            b2 = -b2;
            t = -t;
        }
        return new BigDecimal(Math.exp(b2), MathContext.DECIMAL64).movePointRight(t);
    }

    /**
     * Same as {@link java.lang.Math#pow(double,double)} but returns a {@link BigDecimal} (precision ~ 16).
     * <p>
     * Works even for outputs that fall outside the {@code double} range.
     * <br>
     * The only limitation is that {@code b * log(a)} cannot exceed the {@code double} range. 
     * 
     * @param a Base. Should be non-negative 
     * @param b Exponent. Should be finite (and non-negative if base is zero)
     * @return Returns the value of the first argument raised to the power of the second argument.
     * @see "https://stackoverflow.com/questions/6827516/logarithm-for-biginteger"
     */
    public static BigDecimal pow(double a, double b) {
        if (!(Double.isFinite(a) && Double.isFinite(b)))
            throw new IllegalArgumentException(
                    Double.isFinite(b) ? "base not finite: a=" + a : "exponent not finite: b=" + b);
        if (b == 0)
            return BigDecimal.ONE;
        else if (b == 1)
            return BigDecimal.valueOf(a);
        if (a <= 0) {
            if (a == 0) {
                if (b >= 0)
                    return BigDecimal.ZERO;
                else
                    throw new IllegalArgumentException("0**negative = infinite b=" + b);
            } else
                throw new IllegalArgumentException("negative base a=" + a);
        }
        double x = b * Math.log(a);
        if (Math.abs(x) < MAX_DIGITS_E)
            return BigDecimal.valueOf(Math.pow(a, b));
        else
            return exp(x);
    }

    // @see https://stackoverflow.com/a/56814650
    public static BigInteger floorDiv(BigInteger a, BigInteger b) {
        // divideAndRemainder returns quotient and remainder in array
        BigInteger[] qr = a.divideAndRemainder(b);  
        return qr[0].signum() >= 0 || qr[1].signum() == 0 ? 
             qr[0] : qr[0].subtract(BigInteger.ONE);
    }
}

/* */
