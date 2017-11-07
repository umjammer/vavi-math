/* 
 * Copyright (C) 86, 1995-2005, 2007 Free Software Foundation, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 */

package vavi.math.factor.generator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import vavi.util.Generator;


/**
 * factor -- print prime factors of n.
 * 
 * Written by Paul Rubin <phr@ocf.berkeley.edu>.
 * Adapted for GNU, fixed to factor UINT_MAX by Jim Meyering.
 * 
 * @see "coreutils/src/factor.c"
 */
public class FactorGenerator extends Generator<BigInteger> {

    /*
     * The trial divisor increment wheel. Use it to skip over divisors that are
     * composites of 2, 3, 5, 7, or 11. The part from WHEEL_START up to
     * WHEEL_END is reused periodically, while the "lead in" is used to test for
     * those primes and to jump onto the wheel. For more information, see
     * http://www.utm.edu/research/primes/glossary/WheelFactorization.html
     */

    /** */
    private static final int WHEEL_SIZE = 5;

    /** */
    private static BigInteger[] wheelTable;

    static {
        Scanner scanner = new Scanner(FactorGenerator.class.getResourceAsStream("/wheel.dat"));
        List<BigInteger> temp = new ArrayList<>();
        while (scanner.hasNextLong()) {
            long i = scanner.nextLong();
            temp.add(BigInteger.valueOf(i));
        }
        scanner.close();
        wheelTable = temp.toArray(new BigInteger[temp.size()]);
    }

    /** */
    private static final int WHEEL_START = WHEEL_SIZE;
    /** */
    private static final int WHEEL_END = wheelTable.length;

    /** */
    private static final BigInteger TWO = BigInteger.valueOf(2);

    /** */
    private void factor(BigInteger n0) {
        BigInteger n = n0, d, q;
        int w = 0;

        if (n.compareTo(BigInteger.ONE) <= 0) { // <=
            return;
        }

        /*
         * The exit condition in the following loop is correct because any time
         * it is tested one of these 3 conditions holds: (1) d divides n (2) n
         * is prime (3) n is composite but has no factors less than d. If (1) or
         * (2) obviously the right thing happens. If (3), then since n is
         * composite it is >= d^2.
         */
        d = TWO;
        do {
            q = n.divide(d);
            while (n.equals(q.multiply(d))) {
                yield(d);
                n = q;
                q = n.divide(d);
            }
            d = d.add(wheelTable[w++]);
            if (w == WHEEL_END) {
                w = WHEEL_START;
            }
        } while (d.compareTo(q) <= 0); // <=

        if (!n.equals(BigInteger.ONE) || n0.equals(BigInteger.ONE)) {
            yield(n);
        }
    }

    /** */
    private BigInteger n;

    /** */
    public FactorGenerator(BigInteger n) {
        this.n = n;
    }

    @Override
    public void run() {
        factor(n);
    }
}

/* */
