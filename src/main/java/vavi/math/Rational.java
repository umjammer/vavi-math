/*
 * http://blog.livedoor.jp/p-1956050/archives/22122766.html
 */

package vavi.math;


/**
 * Rational.
 *
 * 有理数
 */
public class Rational extends Number implements Cloneable, Comparable<Rational> {

    /** */
    private int numerator;
    /** */
    private int denominator;

    /** */
    public Rational(int n) {
        numerator = n;
        denominator = 1;
    }

    /** TODO */
    private static final RationalException exception = new RationalException("denominator is zero!");

    /** */
    public Rational(int n, int d) throws RationalException {
        if (d < 0) {
            d *= -1;
            n *= -1;
        } else if (d == 0) {
            throw exception;
        }
        numerator = n;
        denominator = d;
        reduction();
    }

    /** */
    private static final int gcd(int x, int y) {
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
    public Rational clone() throws CloneNotSupportedException {
        try {
            return new Rational(numerator, denominator);
        } catch (RationalException e) {
            return null;
        }
    }

    /** */
    private void reduction() {
        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
    }

    public Rational negate() throws RationalException {
        return new Rational(numerator * -1, denominator);
    }

    public Rational invert() throws RationalException {
        return new Rational(denominator, numerator);
    }

    public Rational add(Rational x) throws RationalException {
        return new Rational(this.numerator * x.denominator + this.denominator * x.numerator, this.denominator * x.denominator);
    }

    public Rational add(int n) throws RationalException {
        return new Rational(numerator + n * denominator, denominator);
    }

    public Rational subtract(Rational x) throws RationalException {
        return new Rational(this.numerator * x.denominator - this.denominator * x.numerator, this.denominator * x.denominator);
    }

    public Rational subtract(int n) throws RationalException {
        return new Rational(numerator - n * denominator, denominator);
    }

    public Rational multiply(Rational x) throws RationalException {
        return new Rational(this.numerator * x.numerator, this.denominator * x.denominator);
    }

    public Rational multiply(int x) throws RationalException {
        return new Rational(numerator * x, denominator);
    }

    public Rational divide(Rational x) throws RationalException {
        return new Rational(this.numerator * x.denominator, this.denominator * x.numerator);
    }

    public Rational divide(int x) throws RationalException {
        return new Rational(numerator, denominator * x);
    }

    /** TODO for performance */
    public boolean equals(Rational x) {
        return this.numerator == x.numerator && this.denominator == x.denominator;
    }

    public boolean equals(Object x) {
        if (x != null && Rational.class.isInstance(x)) {
            Rational r = Rational.class.cast(x);
            return this.numerator == r.numerator && this.denominator == r.denominator;
        } else {
            return false;
        }
    }

    public int compareTo(Rational x) {
        return this.numerator * x.denominator - this.denominator * x.numerator;
    }

    public String toString() {
        return denominator == 1 ? "" + numerator : "" + numerator + "/" + denominator;
    }

    public double doubleValue() {
        double ret = (double) numerator / denominator;
        return ret;
    }

    public float floatValue() {
        float ret = (float) numerator / denominator;
        return ret;
    }

    public int intValue() {
        return numerator / denominator;
    }

    public long longValue() {
        long ret = (long) numerator / denominator;
        return ret;
    }
}

/* */
