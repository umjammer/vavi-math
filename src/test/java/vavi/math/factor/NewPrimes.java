/*
 * https://github.com/KevinLeeCrosby/euler/blob/master/src/main/java/net/euler/utils/NewPrimes.java
 */

package vavi.math.factor;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import vavi.math.Util;
import vavi.util.LongBitSet;

import static java.lang.Math.max;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;


/**
 * Prime number generator (64 bit) and related methods.
 *
 * @author Kevin Crosby
 */
public class NewPrimes implements Iterable<BigInteger> {

    private static volatile NewPrimes instance = null;
    private static LongBitSet sieve;

    private static final int BASE = 30;
    private static int BITS;
    private static List<BigInteger> BASE_PRIMES;
    private static Map<BigInteger, BigInteger> MODULI;
    private static Map<BigInteger, BigInteger> MODULI2;
    private static long BIT_LIMIT, SIEVE_LIMIT;
    static final BigInteger TWO = BigInteger.valueOf(2);

    private NewPrimes(long sieveLimit) {
        initialize();
        generate(sieveLimit);
    }

    public static NewPrimes getInstance(long sieveLimit) {
        if (instance == null) {
            synchronized (NewPrimes.class) {
                if (instance == null) {
                    instance = new NewPrimes(sieveLimit);
                }
            }
        }
        return instance;
    }

    public static NewPrimes getInstance() {
        return getInstance(375000001L);
    }

    private static void initialize() {
        List<BigInteger> basePrimes = new ArrayList<>();
        Map<BigInteger, BigInteger> moduli = new HashMap<>();
        basePrimes.add(TWO);

        final int bitLimit = (BASE - 1) >> 1;
        long lastBasePrime = 2;
        LongBitSet modSieve = new LongBitSet(); // fill with false (inverted logic), for n >= 3;
        long prime = 3;
        long primorial = 6;
        long primeBit = modSieve.nextClearBit(0);
        while (primorial <= BASE) {
            basePrimes.add(BigInteger.valueOf(prime));
            lastBasePrime = prime;
            for (long compositeBit = primeBit + prime; compositeBit <= bitLimit; compositeBit += prime) {
                modSieve.set(compositeBit); // set to composite
            }
            primeBit = modSieve.nextClearBit(primeBit + 1);
            prime = (primeBit << 1) + 3;
            primorial *= prime;
        }

        moduli.put(ZERO, ONE); // NOTE: moduli are not necessarily prime in all bases
        for (long i = 1, modBit = modSieve.nextClearBit(lastBasePrime >> 1), modulus = (modBit << 1) + 3; modBit < bitLimit;
             ++i, modBit = modSieve.nextClearBit(modBit + 1), modulus = (modBit << 1) + 3) {
            moduli.put(BigInteger.valueOf(i), BigInteger.valueOf(modulus));
        }

        BASE_PRIMES = basePrimes;
        MODULI = moduli;
        MODULI2 = new HashMap<>();
        moduli.forEach((k, v) -> MODULI2.put(v, k));
        BITS = MODULI.size();
    }

    /**
     * Black-Key Sieve
     * <p>
     * @see "http://www.qsl.net/w2gl/blackkey.html"
     */
    private static void generate(long sieveLimit) {
        long oddLimit = max(sieveLimit + 1, 41) | 1; // odd number
        while (!MODULI.containsValue(BigInteger.valueOf(oddLimit).mod(BigInteger.valueOf(BASE)))) {
            oddLimit -= 2;
        }
        SIEVE_LIMIT = oddLimit;
        BIT_LIMIT = pack(BigInteger.valueOf(SIEVE_LIMIT)).longValue();
        if (BIT_LIMIT < 0) {
            throw new NumberFormatException("Sieve limit of " + SIEVE_LIMIT + " is too large!");
        }

        sieve = new LongBitSet(); // all bits are initially false, let false = prime, true = composite
        sieve.set(0); // 1 is not a prime
        BigInteger prime = unpack(ONE);
        long primeBit = sieve.nextClearBit(1);
        while (prime.compareTo(BigInteger.valueOf(SIEVE_LIMIT).divide(prime)) <= 0) {
            BigInteger ratio = BigInteger.valueOf(SIEVE_LIMIT).divide(prime);
            long i = 0;
            BigInteger multiplierBit = BigInteger.valueOf(primeBit);
            BigInteger multiplier = unpack(multiplierBit);
            BigInteger composite = prime.multiply(multiplier);
            while (i < BITS && multiplier.compareTo(ratio) <= 0 && composite.compareTo(BigInteger.valueOf(SIEVE_LIMIT)) < 0) {
                for (BigInteger compositeBit = pack(composite); compositeBit.compareTo(BigInteger.valueOf(BIT_LIMIT)) < 0; compositeBit = compositeBit.add(BigInteger.valueOf(BITS).multiply(prime))) {
                    sieve.set(compositeBit.longValue());
                }
                ++i;
                multiplierBit = multiplierBit.add(ONE);
                multiplier = unpack(multiplierBit);
                composite = prime.multiply(multiplier); // prevent overflow
            }
            primeBit = sieve.nextClearBit(primeBit + 1);
            prime = unpack(BigInteger.valueOf(primeBit));
        }
    }

    private static BigInteger unpack(BigInteger bit) {
        BigInteger mod = bit.mod(BigInteger.valueOf(BITS));
        BigInteger offset = bit.divide(BigInteger.valueOf(BITS));
        return BigInteger.valueOf(BASE).multiply(offset).add(MODULI.get(mod)); // number
    }

    private static BigInteger pack(BigInteger number) {
        assert isCoprime(number, BigInteger.valueOf(BASE)) : "Number " + number + " is not coprime with base " + BASE;
        BigInteger invMod = number.mod(BigInteger.valueOf(BASE));
        BigInteger offset = number.divide(BigInteger.valueOf(BASE));
        return BigInteger.valueOf(BITS).multiply(offset).add(MODULI2.get(invMod)); // bit
    }

    /**
     * Get prime at index.
     * NOTE:  This is a O(n) method, since there is no random access to the sieve.
     *
     * @param index Index to get prime at.
     * @return Prime number at index.
     */
    public BigInteger get(long index) {
        assert index >= 0 : "Index must be non-negative!";
        assert index < SIEVE_LIMIT : "Index is too large for existing sieve.";
        long counter = 0;
        for (final BigInteger prime : this) {
            if (counter++ == index) {
                return prime;
            }
        }
        return ZERO;
    }

    public static BigInteger getLargestStoredPrime() {
        BigInteger bit = BigInteger.valueOf(sieve.previousClearBit(BIT_LIMIT - 1));
        return unpack(bit);
    }

    /**
     * Test if a number is prime using the Miller–Rabin Primality Test, which is guaranteed to correctly distinguish
     * composites and primes up to 3,317,044,064,679,887,385,961,981 using the first 13 prime numbers.
     *
     * @param n Number to be tested.
     * @return True only if prime.
     */
    public boolean isPrime(BigInteger n) {
        if (BASE_PRIMES.contains(n)) {
            return true;
        }
        if (n.compareTo(TWO) < 0 || !isCoprime(n, BigInteger.valueOf(BASE))) {
            return false;
        }
        if (n.compareTo(BigInteger.valueOf(23)) <= 0) {
            return true;
        }
        if (n.compareTo(BigInteger.valueOf(SIEVE_LIMIT)) < 0) {
            return !sieve.get(pack(n).longValue());
        }
        BigInteger d = n.subtract(ONE);
        BigInteger s = ZERO;
        while (d.mod(TWO).equals(ZERO)) {
            d = d.shiftRight(1);
            s = s.add(ONE);
        }
        int c = 0;
        for (final BigInteger a : this) {
            if (++c == 13) break;
            if (!a.modPow(d, n).equals(ONE)) {
                boolean composite = true;
                for (BigInteger r = ZERO, p = ONE; r.compareTo(s) < 0; r = r.add(ONE), p = p.shiftLeft(1)) { // p = 2^r
                    if (a.modPow(p.multiply(d), n).equals(n.subtract(ONE))) {
                        composite = false;
                        break; // inconclusive
                    }
                }
                if (composite) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isCoprime(BigInteger a, BigInteger b) {
        return a.gcd(b).equals(ONE);
    }

    public boolean isPerfectCube(BigInteger number) {
        return isPerfectPowerOf(number, BigInteger.valueOf(3));
    }

    public boolean isPerfectPowerOf(BigInteger number, BigInteger degree) {
        return degree.compareTo(ONE) > 0 && degree(number).mod(degree).equals(ZERO);
    }

    public boolean isPerfectPower(BigInteger number) {
        return degree(number).compareTo(ONE) > 0;
    }

    public BigInteger degree(final BigInteger power) {
        assert power.compareTo(ZERO) > 0 : "Number must be positive!";
        List<BigInteger> factors = factor(power);
        BigInteger degree = ZERO;
        for (BigInteger factor : new HashSet<>(factors)) {
            int exponent = Collections.frequency(factors, factor);
            degree = degree.gcd(BigInteger.valueOf(exponent));
        }
        return degree;
    }

    public List<BigInteger> factor(BigInteger number) { // TODO:  replace with Quadratic Sieve?
        return trialDivision(number);
//        if(number < 2L) {
//          return Lists.newArrayList();
//        }
//        if(isPrime(number)) {
//          return Lists.newArrayList(number);
//        }
//        long divisor = rho(number);
//        if(divisor == 1 || divisor == number) {
//          return trialDivision(number); // rho failed
//        }
//        List<Long> factors = factor(divisor);
//        factors.addAll(factor(number / divisor));
//        factors.sort(Comparator.naturalOrder());
//
//        return factors;
    }

    SecureRandom random = new SecureRandom();

    // Pollard-Brent Rho Factorization
    // NOTE:  doesn't work well for large semiprimes, such as 341550071728321 = 10670053 * 32010157 !
    // https://comeoncodeon.wordpress.com/2010/09/18/pollard-rho-brent-integer-factorization
    private BigInteger rho(BigInteger n) {
        if ((n.and(ONE)).equals(ZERO)) {
            return TWO;
        }

        BigInteger y = new BigInteger(n.bitLength(), random);
        BigInteger c = new BigInteger(n.bitLength(), random);
        BigInteger m = new BigInteger(n.bitLength(), random);
        BigInteger g = ONE, r = ONE, q = ONE;
        BigInteger x = y, ys = y;
        while (g.equals(ONE)) {
            x = y;
            for (BigInteger i = ZERO; i.compareTo(r) < 0; i = i.add(ONE)) {
                y = ((y.multiply(y)).mod(n).add(c)).mod(n);
            }
            BigInteger k = ZERO;
            while (k.compareTo(r) < 0 && g.equals(ONE)) {
                ys = y;
                for (BigInteger i = ZERO; i.compareTo(m.min(r.subtract(k))) < 0; i = i.add(ONE)) {
                    y = ((y.multiply(y)).mod(n).add(c)).mod(n);
                    q = q.multiply(x.subtract(y).abs()).mod(n);
                }
                g = q.gcd(n);
                k = k.add(m);
            }
            r = r.multiply(TWO);
        }
        if (g.equals(n)) {
            do {
                ys = ((ys.multiply(ys)).mod(n).add(c)).mod(n);
                g = x.subtract(ys).abs().gcd(n);
            } while (g.equals(ONE));
        }
        return g;
    }

    private List<BigInteger> trialDivision(BigInteger number) {
        List<BigInteger> factors = new ArrayList<>();
        if (number.compareTo(TWO) < 0) {
            return factors;
        }

        BigInteger root;
        root = Util.sqrt(number);
        boolean updated;
        for (final BigInteger prime : this) { // trial division
            updated = false;
            if (prime.compareTo(root) > 0) {
                break;
            }
            while (number.mod(prime).equals(ZERO)) {
                number = number.divide(prime);
                factors.add(prime);
                updated = true;
            }
            if (updated) {
                root = Util.sqrt(number);
            }
        }
        if (number.compareTo(ONE) > 0) {
            factors.add(number);
        }

        return factors;
    }

    public List<BigInteger> divisors(BigInteger number) {
        List<BigInteger> factors = factor(number);
        if (factors.isEmpty()) {
            return number.equals(ONE) ? new ArrayList<>(1) : new ArrayList<>();
        }

        List<BigInteger> divisors = new ArrayList<>(1);
        for (final BigInteger factor : new HashSet<>(factors)) {
            BigInteger product = ONE;
            List<BigInteger> results = new ArrayList<>();
            int frequency = Collections.frequency(factors, factor);
            for (int exponent = 1; exponent <= frequency; ++exponent) {
                product = product.multiply(factor);
                final BigInteger finalProduct = product;
                divisors.stream()
                        .map(finalProduct::multiply)
                        .forEach(results::add);
            }
            divisors.addAll(results);
        }

        Collections.sort(divisors);
        return divisors;
    }

    public BigInteger countDivisors(BigInteger number) { // Highly composite number formula
        if (number.compareTo(ONE) < 0) {
            return ZERO;
        }
        BigInteger count = ONE;
        List<BigInteger> factors = factor(number);
        for (final BigInteger factor : new HashSet<>(factors)) {
            int exponent = Collections.frequency(factors, factor);
            count = count.multiply(BigInteger.valueOf(exponent + 1));
        }
        return count;
    }

    public BigInteger sumDivisors(BigInteger number) {
        if (number.compareTo(ONE) < 0) {
            return ZERO;
        }
        BigInteger sum = ONE;
        List<BigInteger> factors = factor(number);
        for (final BigInteger factor : new HashSet<>(factors)) {
            int exponent = Collections.frequency(factors, factor);
            BigInteger numerator = factor.pow(exponent + 1).subtract(ONE);
            BigInteger denominator = factor.subtract(ONE);
            sum = sum.multiply(numerator.divide(denominator));
        }
        return sum;
    }

    public BigInteger sigma(BigInteger number, final int power) {
        if (power < 0) {
            throw new IllegalArgumentException("Power cannot be negative!");
        }
        if (power == 0) {
            return countDivisors(number);
        }
        if (number.compareTo(ONE) < 0) {
            return ZERO;
        }
        BigInteger sum = ONE;
        List<BigInteger> factors = factor(number);
        for (final BigInteger factor : new HashSet<>(factors)) {
            int exponent = Collections.frequency(factors, factor);
            BigInteger numerator = factor.pow((exponent + 1) * power).subtract(ONE);
            BigInteger denominator = factor.pow(power).subtract(ONE);
            sum = sum.multiply(numerator.divide(denominator));
        }
        return sum;
    }

    public BigInteger aliquotSum(BigInteger number) { // a.k.a. sum proper divisors
        return sumDivisors(number).subtract(number); // make it proper
    }

    public boolean isPerfectNumber(BigInteger number) {
        return aliquotSum(number).equals(number);
    }

    public boolean isAlmostPerfectNumber(BigInteger number) {
        return aliquotSum(number).equals(number.subtract(ONE));
    }

    public boolean isAbundantNumber(BigInteger number) {
        return aliquotSum(number).compareTo(number) > 0;
    }

    public boolean isDeficientNumber(BigInteger number) {
        return aliquotSum(number).compareTo(number) < 0;
    }

    /**
     * Euler's totient or phi function, φ(n), is an arithmetic function that counts the totatives of n, that is, the
     * positive integers less than or equal to n that are relatively prime to n. Thus, if n is a positive integer, then
     * φ(n) is the number of integers k in the range 1 ≤ k ≤ n for which the greatest common divisor gcd(n, k) = 1.
     *
     * @param number Positive whole number to take totient of.
     * @return Euler's totient.
     */
    public BigInteger totient(BigInteger number) {
        assert number.compareTo(ZERO) > 0 : "Number must be positive!";
        List<BigInteger> factors = factor(number);
        BigInteger phi = number;
        for (final BigInteger factor : new HashSet<>(factors)) {
            phi = phi.divide(factor).multiply(factor.subtract(ONE));
        }
        return phi;
    }

    @Override
    public Iterator<BigInteger> iterator() {
        return new NewPrimeIterator();
    }

    private static class NewPrimeIterator implements Iterator<BigInteger> {
        private long bit;
        private int baseCount;

        public NewPrimeIterator() {
            bit = 1;
            baseCount = 0;
        }

        public boolean hasNext() {
            return bit < BIT_LIMIT; // TODO generalize
        }

        public BigInteger next() {
            BigInteger prime;
            if (baseCount < BASE_PRIMES.size()) {
                prime = BASE_PRIMES.get(baseCount++);
            } else {
                prime = unpack(BigInteger.valueOf(bit));
                bit = sieve.nextClearBit(bit + 1);
            }
            return prime;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        {
            NewPrimes primes = NewPrimes.getInstance();
            for (long number : Arrays.asList(105L, 10053L, 1005415L, 10054033243L)) {
                System.out.println(number + " is " + (primes.isPrime(BigInteger.valueOf(number)) ? "prime!" : "composite!"));
            }
            for (long number : Arrays.asList(997L, 40487L, 53471161L, 1645333507L, 188748146801L)) {
                System.out.println(number + " is " + (primes.isPrime(BigInteger.valueOf(number)) ? "prime!" : "composite!"));
            }
        }

        for (int limit : Arrays.asList(10, 20, 30, 15)) {
            //    for(int limit : Lists.newArrayList(1000)) {
            NewPrimes primes = NewPrimes.getInstance();
            int i = 1;
            for (BigInteger prime : primes) {
                System.out.print(prime + " ");
                if (i++ == limit) {
                    System.out.println();
                    break;
                }
            }
        }

        {
            NewPrimes primes = NewPrimes.getInstance();

            // test O(n) get method
            for (int i = 24; i >= 0; --i) {
                System.out.print(primes.get(i) + " ");
            }
            System.out.println();
        }

        {
            //      NewPrimes primes = NewPrimes.getInstance();
            //    int i = 1;
            //    for (long prime : primes) {
            //      if (!primes.isPrime(prime)) {
            //        System.err.println(prime + " is NOT prime!");
            //      }
            //      System.out.print(prime + " ");
            //      if (i++ % 10 == 0) {
            //        System.out.println();
            //      }
            //    }
        }

        {
            NewPrimes primes = NewPrimes.getInstance();
            long number = 60;
            System.out.println("factors of " + number + " = " + primes.factor(BigInteger.valueOf(number)));
            System.out.println("divisors of " + number + " = " + primes.divisors(BigInteger.valueOf(number)));
            System.out.println("number of divisors of " + number + " = " + primes.countDivisors(BigInteger.valueOf(number)));
            System.out.println("sum of divisors of " + number + " = " + primes.sumDivisors(BigInteger.valueOf(number)));
            System.out.println("totient of " + number + " = " + primes.totient(BigInteger.valueOf(number)));

            System.out.println();
            System.out.println("Largest prime stored is " + NewPrimes.getLargestStoredPrime());
        }
    }
}
