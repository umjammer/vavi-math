/*
 * https://stackoverflow.com/questions/6827516/logarithm-for-biginteger
 */

package vavi.math.factor.generator;

import java.util.function.BiConsumer;


/**
 * Implements an extremely efficient Serial totient(phi) calculator
 * <p>
 * This implements an optimized windowed Sieve of Eratosthenes.  The
 * window size is set at sqrt(N) both to optimize collecting and
 * applying all of the Primes below sqrt(N), and to minimize
 * window-turning overhead.
 * <pre>
 *  CPU complexity is O( N * Log(Log(N)) ), which is virtually linear.
 *
 *  MEM Complexity is O( sqrt(N) ).
 * </pre>
 * This is probalby the ideal combination, as any attempt to further
 * reduce memory will almost certainly result in disproportionate increases
 * in CPU complexity, and vice-versa.
 */
public class TotientSerialCalculator {

    static class NumberFactors {
        /** the part of the number that still needs to be factored */
        private long unFactored;
        private long phi;
    }

    private long reportInterval;
    /** the last value in the previous window */
    private long prevLast;
    /** the first value in this windows range */
    private long firstValue;
    private long windowSize;
    /** the last value in this windows range */
    private long lastValue;
    /** the first value in the next window */
    private long nextFirst;

    // Array that stores all of the NumberFactors in the current window.
    // this is the primary memory consumption for the class and it
    // is 16 * sqrt(N) Bytes, which is O(sqrt(N)).
    public NumberFactors[] numbers;
    // For N=10^12 (1 trilion), this will be 16MB, which should be bearable anywhere.
    // (note that the Primes() array is a secondary memory consumer
    // at O(pi(sqrt(N)), which will be within 10x of O(sqrt(N)))

    // NOTE: this part looks like it did not convert correctly
    public BiConsumer<Long, Long> emitTotientPair;

    /**
     * The Routine To Call
     * Routine to Emit Totient pairs {k, Phi(k)} for k = 1 to N
     * @see 2009-07-14, RBarryYoung
     */
    public void emitTotientPairsToN(long N) {
        long i;
        long k; // the current number being factored
        long p; // the current prime factor
        // Establish the Window frame:
        // note: WindowSize is the critical value that controls both memory
        // usage and CPU consumption and must be sqrt(N) for it to work optimally.
        windowSize = (long) Math.ceil(Math.sqrt((double) N));
        numbers = new NumberFactors[(int) windowSize];
        mapWindow(1);
        boolean isFirstWindow = true;
        reportInterval = (N / 100);
        // Allocate the primes array to hold the primes list
        // Only primes <= sqrt(N) are needed for factoring
        // piMax(X) is a Max estimate of the number of primes <= X
        long[] primes = new long[(int) piMax(windowSize)];
        long primeIndex;
        long nextPrime;
        // init the primes list and its pointers
        primes[0] = 2;
        // "prime" the primes list with the first prime
        nextPrime = 1;
        // Map (and Remap) the window with sqrt(N) numbers, sqrt(N) times to
        // sequentially map all of the numbers <= N.
        while (firstValue <= N) {
            primeIndex = 0;
            // note: cant use enumerator for the loop below because NextPrime
            //  changes during the first window as new primes <= SQRT(N) are accumulated
            while (primeIndex < nextPrime) {
                // get the next prime in the list
                p = primes[(int) primeIndex];
                // find the first multiple of (p) in the current window range
                k = prevLast + (p - (prevLast % p));
                while (k < nextFirst) {
                    numbers[(int) (k - firstValue)].unFactored = numbers[(int) (k - firstValue)].unFactored / p;
                    // always works the first time'
                    numbers[(int) (k - firstValue)].phi = numbers[(int) (k - firstValue)].phi * (p - 1);
                    while (numbers[(int) (k - firstValue)].unFactored % p == 0) {
                        numbers[(int) (k - firstValue)].unFactored = numbers[(int) (k - firstValue)].unFactored / p;
                        numbers[(int) (k - firstValue)].phi = numbers[(int) (k - firstValue)].phi * p;
                    }

                    // skip ahead to the next multiple of p:
                    // (this is what makes it so fast, never have to try prime factors that dont apply)
                    k = (k + p);
                    // repeat until we step out of the current window:
                }

                // if this is the first window, then scan ahead for primes
                if (isFirstWindow) {
                    for (i = primes[(int) (nextPrime - 1)] + 1; i <= (p ^ (2 - 1)); i++) {
                        // the range of possible new primes
                        // Don't go beyond the first window
                        if ((i >= windowSize)) {
                            break;
                        }

                        if ((numbers[(int) (i - firstValue)].unFactored == i)) {
                            // this is a prime less than SQRT(N), so add it to the list.
                            primes[(int) nextPrime] = i;
                            nextPrime++;
                        }
                    }
                }

                primeIndex++; // move to the next prime
            }

            // Now Finish & Emit each one
            for (k = firstValue; k <= lastValue; k++) {
                // Primes larger than sqrt(N) will not be finished:
                if ((numbers[(int) (k - firstValue)].unFactored > 1)) {
                    // Not done factoring, must be an large prime factor remaining:
                    numbers[(int) (k - firstValue)].phi = numbers[(int) (k - firstValue)].phi * (numbers[(int) (k - firstValue)].unFactored - 1);
                    numbers[(int) (k - firstValue)].unFactored = 1;
                }

                // Emit the value pair: (k, Phi(k))
                emitPhi(k, numbers[(int) (k - firstValue)].phi);
            }

            // re-Map to the next window
            isFirstWindow = false;
            mapWindow(nextFirst);
        }
    }

    /**
     * just a placeholder for now, that raises an event to the display form
     * periodically for reporting purposes.  Change this to do the actual
     * emitting.
     */
    void emitPhi(long k, long phi) {
        if ((k % reportInterval) == 0) {
            emitTotientPair.accept(k, phi);
        }
    }

    /**
     * Efficiently reset the window so that we do not have to re-allocate it.
     * init all of the boundary values
     */
    public void mapWindow(long FirstVal) {
        firstValue = FirstVal;
        prevLast = firstValue - 1;
        nextFirst = firstValue + windowSize;
        lastValue = nextFirst - 1;
        // Initialize the Numbers prime factor arrays
        for (int i = 0; i < windowSize; i++) {
            numbers[i].unFactored = i + firstValue; // initially equal to the number itself
            numbers[i].phi = 1; // starts at mulplicative identity(1)
        }
    }

    /**
     * estimate of pi(n) == {primes <= (n)} that is
     * never less than the actual number of primes.
     * @see "P. Dusart, 1999"
     */
    long piMax(long x) {
        return (long) ((x / Math.log(x)) * (1 + (1.2762 / Math.log(x))));
    }
}

/* */
