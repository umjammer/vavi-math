/*
 * http://www.ryan-h.com/uncategorized/fast-java-prime-number-generation/
 */

package vavi.math.prime.generator;

import java.util.Arrays;

import vavi.util.Generator;


/**
 * Prime generator based on Sieve of Eratosthenes
 */
public class SievePrimeGenerator extends Generator<Integer> {

    private void getPrimes(int maxPrime) {

        boolean[] primes = new boolean[maxPrime + 1];

        Arrays.fill(primes, true);
        primes[0] = false;
        primes[1] = false;
        primes[2] = true;
        int rootMP = (int) Math.floor(Math.sqrt(maxPrime));

        // get next prime
        for (int i = 2; i <= rootMP;) {

            // use prime to mark off all multiples as not prime
            for (int j = (i * 2); j <= maxPrime; j += i) {
                primes[j] = false;
            }

            do {
                i++;
            } while (primes[i] == false);
            // System.out.println(i);
        }

        for (int i = 0; i < maxPrime; i++) {
            if (primes[i]) {
                yield(i);
            }
        }
    }

    /** */
    private int maxPrime;

    /** */
    public SievePrimeGenerator(int maxPrime) {
        this.maxPrime = maxPrime;
    }

    @Override
    public void run() {
        getPrimes(maxPrime);
    }
}
