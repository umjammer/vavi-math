/*
 * http://www.merriampark.com/comb.htm
 */

package vavi.math;

import java.math.BigInteger;
import java.util.Iterator;


/**
 * Systematically generate combinations.
 */
public class CombinationGenerator implements Iterator<int[]>, Iterable<int[]> {
    private int[] a;
    private int n;
    private int r;
    private BigInteger numLeft;
    private BigInteger total;

    /** 
     * @param n selected
     * @param r selection
     * @throws IllegalArgumentException when r > n, n < 1
     */
    public CombinationGenerator(int n, int r) {
        if (r > n) {
            throw new IllegalArgumentException("r > n");
        }
        if (n < 1) {
            throw new IllegalArgumentException("n < 1");
        }
        this.n = n;
        this.r = r;
        a = new int[r];
        BigInteger nFact = getFactorial(n);
        BigInteger rFact = getFactorial(r);
        BigInteger nminusrFact = getFactorial(n - r);
        total = nFact.divide(rFact.multiply(nminusrFact));
        reset();
    }

    /** Reset */
    public void reset() {
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        numLeft = new BigInteger(total.toString());
    }

    /** Return number of combinations not yet generated */
    public BigInteger getNumLeft() {
        return numLeft;
    }

    /** Are there more combinations? */
    public boolean hasNext() {
        return numLeft.compareTo(BigInteger.ZERO) == 1;
    }

    /** Return total number of combinations */
    public BigInteger getTotal() {
        return total;
    }

    /** Compute factorial */
    private static BigInteger getFactorial(int n) {
        BigInteger fact = BigInteger.ONE;
        for (int i = n; i > 1; i--) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }

    /**
     * Generate next combination (algorithm from Rosen p. 286)
     * @return always same instance
     */
    public int[] next() {

        if (numLeft.equals(total)) {
            numLeft = numLeft.subtract(BigInteger.ONE);
            return a;
        }

        int i = r - 1;
        while (a[i] == n - r + i) {
            i--;
        }
        a[i] = a[i] + 1;
        for (int j = i + 1; j < r; j++) {
            a[j] = a[i] + j - i;
        }

        numLeft = numLeft.subtract(BigInteger.ONE);
        return a;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<int[]> iterator() {
        return this;
    }
}

/* */
