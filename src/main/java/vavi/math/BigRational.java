/*
 * http://d.hatena.ne.jp/pcl/20110330/p1
 */

package vavi.math;

import java.math.BigInteger;
import java.math.BigDecimal;


public class BigRational extends Number implements Comparable<BigRational> {

    private BigInteger numerator;

    private BigInteger denominator;

    public BigInteger getNumerator() {
        return numerator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    private static final BigDecimal gcd(BigDecimal a, BigDecimal b) {
        if (a.compareTo(b) < 0) {
            return gcd(b, a);
        } else {
            BigDecimal m = a.remainder(b);
            if (m.compareTo(BigDecimal.ZERO) == 0) {
                return b;
            } else {
                return gcd(b, m);
            }
        }
    }

    public BigRational(long a) {
        this(a, 1);
    }

    public BigRational(BigInteger a) {
        this(a, BigInteger.ONE);
    }

    public BigRational(BigDecimal a) {
        this(a, BigDecimal.ONE);
    }

    public BigRational(long a, long b) {
        this(BigInteger.valueOf(a), BigInteger.valueOf(b));
    }

    public BigRational(BigInteger a, BigInteger b) {
        if (a.compareTo(BigInteger.ZERO) == 0) {
            this.numerator = a;
            this.denominator = BigInteger.ONE;
        } else {
            BigInteger d = a.gcd(b);
            this.numerator = a.divide(d);
            this.denominator = b.divide(d);
        }
    }

    public BigRational(BigDecimal a, BigDecimal b) {
        if (a.compareTo(BigDecimal.ZERO) == 0) {
            this.numerator = a.toBigInteger();
            this.denominator = BigInteger.ONE;
        } else {
            BigDecimal d = gcd(a, b);
            this.numerator = a.divide(d).toBigInteger();
            this.denominator = b.divide(d).toBigInteger();
        }
    }

    public BigRational add(BigRational b) {
        return new BigRational(this.numerator.multiply(b.getDenominator()).add(b.getNumerator().multiply(this.denominator)),
                               this.denominator.multiply(b.getDenominator()));
    }

    public BigRational subtract(BigRational b) {
        return new BigRational(this.numerator.multiply(b.getDenominator())
                .subtract(b.getNumerator().multiply(this.denominator)), this.denominator.multiply(b.getDenominator()));
    }

    public BigRational multiply(BigRational b) {
        return new BigRational(this.numerator.multiply(b.getNumerator()), this.denominator.multiply(b.getDenominator()));
    }

    public BigRational divide(BigRational b) {
        return new BigRational(this.numerator.multiply(b.getDenominator()), this.denominator.multiply(b.getNumerator()));
    }

    @Override
    public boolean equals(Object x) {
        if (x instanceof BigRational) {
            BigRational r = (BigRational) x;
            return this.numerator.equals(r.numerator) && this.denominator.equals(r.denominator);
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(BigRational b) {
        BigInteger x = this.numerator.multiply(b.getDenominator());
        BigInteger y = b.getNumerator().multiply(this.denominator);
        return x.compareTo(y);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.numerator);
        if (this.getDenominator().compareTo(BigInteger.ONE) > 0) {
            sb.append("/").append(this.denominator);
        }
        return sb.toString();
    }

    @Override
    public double doubleValue() {
        return numerator.divide(denominator).doubleValue();
    }

    @Override
    public float floatValue() {
        return numerator.divide(denominator).floatValue();
    }

    @Override
    public int intValue() {
        return numerator.divide(denominator).intValue();
    }

    @Override
    public long longValue() {
        return numerator.divide(denominator).longValue();
    }
}
