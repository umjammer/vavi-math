
package vavi.math.prime.generator;

import java.util.ArrayList;
import java.util.List;

import vavi.util.Generator;


/**
 * Check the speed of the various PrimeGenerators
 */
public class SpeedTest {

    /*
Timning generating list of primes between 0 to 1500

Baseline
--- vavi.math.prime.BitSetOddSkippingSievePG
est Time = 2.41 est Time = 0.70, 239 primes sum=165040
--- vavi.math.prime.OddSkippingSievePG
est Time = 1.09 est Time = 0.32, 239 primes sum=165040
--- vavi.math.prime.SkippingSievePG
est Time = 0.92 est Time = 0.27, 239 primes sum=165040
--- vavi.math.prime.SievePG
est Time = 0.99 est Time = 0.29, 239 primes sum=165040
--- vavi.math.prime.SimplePG
est Time = 11.65 est Time = 3.40, 239 primes sum=165040
--- vavi.math.prime.SimpleOddPG
est Time = 2.82 est Time = 0.82, 239 primes sum=165040
     */
    public static void main(String[] args) {

        long startTime;
        long estimatedTime;
        Iterable<Integer> primeNums;
        int max = 1500;// (int) Math.pow(10, 8); //Integer.MAX_VALUE / (8 * 8 * 8 * 8 * 2);
        long sum = 0;

        System.out.println("Timning generating list of primes between 0 to " + max);

        // Baseline
        System.out.println("\nBaseline");
        // real 
        primeNums = new SievePrimeGenerator(max);
        primeNums = new SievePrimeGenerator(max);
        startTime = System.nanoTime();
        primeNums = new SievePrimeGenerator(max);
        for (int p : primeNums) {
            sum += p;
        }
        estimatedTime = System.nanoTime() - startTime;
        long baseTime = estimatedTime;

        // Setup ready to run all Prime Generators
        List<Generator<Integer>> pgs = new ArrayList<>();
        pgs.add(new BitSetOddSkippingSievePrimeGenerator(max));
        pgs.add(new OddSkippingSievePrimeGenerator(max));
        pgs.add(new SkippingSievePrimeGenerator(max));
        pgs.add(new SievePrimeGenerator(max));
        pgs.add(new SimplePrimeGenerator(max));
        pgs.add(new SimpleOddPrimeGenerator(max));

        //
        for (Generator<Integer> pg : pgs) {
            System.gc();
            System.gc();
            System.gc();
            System.gc();
            System.out.println("--- " + pg.getClass().getName());
            startTime = System.nanoTime();
            primeNums = pg;
            sum = 0;
            int c = 0;
            for (int p : primeNums) {
                sum += p;
                c++;
            }
            estimatedTime = System.nanoTime() - startTime;
//            long estimatedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            System.out.printf("est Time = %.2f", (double) estimatedTime / baseTime);
            System.out.printf(" est Time = %.2f", estimatedTime / 1000000d);
            System.out.println(", " + c + " primes sum=" + sum);
        }
    }
}
