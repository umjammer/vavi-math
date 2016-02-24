/*
 * Copyright (c) 2012 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math;

import java.math.BigInteger;
import java.util.Scanner;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * PermutationGeneratorTest. 
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2012/11/01 umjammer initial version <br>
 */
public class PermutationGeneratorTest {

    @Test
    public void test() {
        Scanner scanner = new Scanner(CombinationGeneratorTest.class.getResourceAsStream("/vavi/math/pg.dat"));
        for (int i = 1; i < 50; i++) {
            PermutationGenerator x = new PermutationGenerator(i);
            BigInteger a = new BigInteger(scanner.next());
            assertEquals(a, x.getTotal());
        }
        scanner.close();
    }

    @Test
    public void test2() {
        Scanner scanner = new Scanner(CombinationGeneratorTest.class.getResourceAsStream("/vavi/math/pg2.dat"));
        for (int i = 5; i < 50; i++) {
            PermutationGenerator x = new PermutationGenerator(i, 4);
            BigInteger a = new BigInteger(scanner.next());
            assertEquals(a, x.getTotal());
        }
        scanner.close();
    }

    /** */
    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 50; i++) {
            PermutationGenerator x = new PermutationGenerator(i);
            System.out.printf("%2d: %d\n", i, x.getTotal());
        }
    }
}

/* */
