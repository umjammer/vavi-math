/*
 * http://www.ryan-h.com/uncategorized/fast-java-prime-number-generation/
 */

package vavi.math.prime.generator;

import java.util.BitSet;

import vavi.util.Generator;


/**
 * Prime generator based on Sieve of Eratosthenes {@link SievePrimeGenerator},
 * improve speed/memory making the boolean array represent odd numbers instead
 * of all numbers. Then use the ideas from {@link ISievePrimeGenerator}. ie:
 * When sieving using the prime 3 instead of sieving 6,9,12,15,18,21 we can
 * actually sieve 9,15,21 ie jump double our prime. each time.
 */
public class BitSetOddSkippingSievePrimeGenerator extends Generator<Integer> {

    private void getPrimes(int maxPrime) {

        /**
         * array where index represents odd number, eg if primes[x] = true then
         * (x*2)+1 is prime.
         */
        BitSet primes = new BitSet(maxPrime / 2 + 1);
        primes.set(0, primes.size(), true);

        primes.set(0, false);
        primes.set(1, true);
        yield(2);
        int rootMP = (int) Math.floor(Math.sqrt(maxPrime));
        int halfMax = maxPrime / 2;

        // get next prime
        for (int i = 3; i <= rootMP;) {
            // use prime to amrk off all mutiples as not prime
            for (int j = ((i * 3) / 2); j <= halfMax; j += i) {
                primes.set(j, false);
            }

            // just want to move up index's while not prime
            // rolled out like this to prevent needing division to get index
            i += 2;
            int k = i / 2;
            while (primes.get(k) == false) {
                k++;
            }
            i = (k * 2) + 1;
        }

        // the index's represent odd numbers starting with 1
        for (int i = 0; i <= halfMax; i++) {
            if (primes.get(i)) {
                yield((i * 2) + 1);
            }
        }
    }

    /** */
    private int maxPrime;

    /** */
    public BitSetOddSkippingSievePrimeGenerator(int maxPrime) {
        this.maxPrime = maxPrime;
    }

    @Override
    public void run() {
        getPrimes(maxPrime);
    }
}
