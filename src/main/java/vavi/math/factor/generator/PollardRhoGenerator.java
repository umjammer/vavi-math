/*
 * https://introcs.cs.princeton.edu/java/99crypto/PollardRho.java.html
 */

package vavi.math.factor.generator;

import java.math.BigInteger;

import vavi.util.Generator;


/**
 * Compilation: javac PollardRho.java Execution: java PollardRho N
 *
 * Factor N using the Pollard-Rho method.
 *
 * % java PollardRho 44343535354351600000003434353 149 329569479697
 * 903019357561501
 */
public class PollardRhoGenerator extends Generator<BigInteger> {

    /** */
    private PollardRho pollardRho;

    /** */
    private BigInteger n;

    /** */
    public PollardRhoGenerator(BigInteger n) {
        pollardRho = new PollardRho(r -> yield(r));
        this.n = n;
    }

    @Override
    public void run() {
        pollardRho.factor(n);
    }
}
