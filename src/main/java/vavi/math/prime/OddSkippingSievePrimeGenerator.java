/*
 * http://www.ryan-h.com/uncategorized/fast-java-prime-number-generation/
 */

package vavi.math.prime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * Prime generator based on Sieve of Eratosthenes {@link vavi.math.prime.generator.SievePrimeGenerator},
 * improve speed/memory making the boolean array represent odd numbers instead
 * of all numbers. Then use the ideas from {@link vavi.math.prime.generator.SievePrimeGenerator}. ie:
 * When sieving using the prime 3 instead of sieving 6,9,12,15,18,21 we can
 * actually sieve 9,15,21 ie jump double our prime. each time.
 */
public class OddSkippingSievePrimeGenerator implements Iterator<Integer>, Iterable<Integer> {

    /** */
    private List<Integer> primes = new ArrayList<>();

    /**
     * Retrieve a list of prime numbers between 0 to maxPrime
     * @param maxPrime highest number to check for primeness, must be less than Integer.MAX_VALUE-2
     */
    public OddSkippingSievePrimeGenerator(int maxPrime) {

        /**
         * array where index represents odd number, eg if primes[x] = true then
         * (x*2)+1 is prime.
         */
        boolean[] primeFlags = new boolean[maxPrime / 2 + 1];

        Arrays.fill(primeFlags, true);
        primeFlags[0] = false; // this actually represents 1
        primeFlags[1] = true; // this actually represents 3
        primes.add(2);
        int rootMP = (int) Math.floor(Math.sqrt(maxPrime));
        int halfMax = maxPrime / 2;

        // get next prime
        for (int i = 3; i <= rootMP;) {
            // use prime to amrk off all mutiples as not prime
            for (int j = ((i * 3) / 2); j <= halfMax; j += i) {
                primeFlags[j] = false;
            }

            // just want to move up index's while not prime
            // rolled out like this to prevent needing division to get index
            i += 2;
            int k = i / 2;
            while (primeFlags[k] == false) {
                k++;
            }
            i = (k * 2) + 1;
        }

        for (int i = 0; i <= halfMax; i++) {
            if (primeFlags[i]) {
                primes.add((i * 2) + 1);
            }
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }

    /** */
    private Iterator<Integer> inner;

    @Override
    public boolean hasNext() {
        if (inner == null) {
            inner = primes.iterator();
        }
        return inner.hasNext();
    }

    @Override
    public Integer next() {
        if (inner == null) {
            inner = primes.iterator();
        }
        return inner.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
