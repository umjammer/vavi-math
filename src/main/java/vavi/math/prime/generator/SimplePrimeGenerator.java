/*
 * http://www.ryan-h.com/uncategorized/fast-java-prime-number-generation/
 */

package vavi.math.prime.generator;

import vavi.util.Generator;


/**
 * starts at 3 and works up to maxPrime checking odd numbers for primeness. To
 * check if a number X is prime it checks for a remainder (modulus) when
 * dividing. It tries dividing X by all numbers from 3 to X/2, again odd numbers
 * only.
 */
public class SimplePrimeGenerator extends Generator<Integer> {

    private void getPrimes(int maxPrime) {

        /*
         * cycle through checking if a numbers prime. To save time, only check
         * odd numbers. Since we are only checking odd numbers we only have to
         * consider odd multiples.
         */
        for (int i = 2; i <= maxPrime; i++) {
            boolean isPrime = true;
//            int highestFactorPossible = i / 2;

            // Since a number should only divide by 2 and itself.
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                yield(i);
            }
        }
    }

    /** */
    private int maxPrime;

    /** */
    public SimplePrimeGenerator(int maxPrime) {
        this.maxPrime = maxPrime;
    }

    @Override
    public void run() {
        getPrimes(maxPrime);
    }
}
