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
import java.util.Iterator;

import vavi.math.prime.generator.OddSkippingSievePrimeGenerator;
import vavi.util.Generator;


/**
 * Goedel.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2009/07/31 nsano initial version <br>
 * @see "http://en.wikipedia.org/wiki/G%C3%B6del_numbering"
 */
public class GoedelGenerator extends Generator<Integer> {

    /** */
    private void goedel(BigInteger n0) {

        BigInteger n = n0, q;

        int goedel = 0;

//        int maxPrime = FactorUtil.maxFactor(n0).intValue();
        int maxPrime = 1500;

        Iterator<Integer> i = new OddSkippingSievePrimeGenerator(maxPrime).iterator();
        BigInteger d = new BigInteger(String.valueOf(i.next()));
        do {
            q = n.divide(d);
            while (n.equals(q.multiply(d))) {
                goedel++;
                n = q;
                q = n.divide(d);
            }
            yield(goedel);
//System.err.println(d + ", " + goedel);
            goedel = 0;
            d = new BigInteger(String.valueOf(i.next()));
        } while (d.compareTo(q) <= 0); // <=
    }

    /** */
    private BigInteger n;

    /** */
    public GoedelGenerator(final BigInteger n) {
        this.n = n;
    }

    @Override
    public void run() {
        goedel(n);
    }
}

/* */
