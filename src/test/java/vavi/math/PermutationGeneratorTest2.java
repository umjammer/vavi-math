/*
 * Copyright (c) 2009 by Naohide Sano, All rights rserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math;

import java.util.Scanner;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * permutation generator.
 *
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (vavi)
 * @version 0.00 090619 nsano initial version <br>
 */
public class PermutationGeneratorTest2 {

    /** */
    @Test
    public void test() throws Exception {
        final String[] elements = { "a", "b", "c", "d", "e", "f", "g" };
        Scanner scanner = new Scanner(CombinationGeneratorTest.class.getResourceAsStream("/vavi/math/t34.dat"));
        for (int[] indices : new PermutationGenerator(elements.length)) {
            StringBuffer permutation = new StringBuffer();
            for (int i = 0; i < indices.length; i++) {
                permutation.append(elements[indices[i]]);
            }
            assertEquals(scanner.next(), permutation.toString());
        }
        scanner.close();
    }

    /** */
    @Test
    public void test2() throws Exception {
        final String[] elements = { "a", "b", "c", "d", "e", "f", "g" };
        Scanner scanner = new Scanner(CombinationGeneratorTest.class.getResourceAsStream("/vavi/math/t34.dat"));
        for (int[] indices : new PermutationGenerator2(elements.length)) {
            StringBuffer permutation = new StringBuffer();
            for (int i = 0; i < indices.length; i++) {
                permutation.append(elements[indices[i]]);
            }
            assertEquals(scanner.next(), permutation.toString());
        }
        scanner.close();
    }

    /** */
    public static void main(String[] args) throws Exception {
        int[] indices;
        String[] elements = { "a", "b", "c", "d", "e", "f", "g" };
        PermutationGenerator x = new PermutationGenerator(elements.length);
        StringBuffer permutation;
        while (x.hasNext()) {
            permutation = new StringBuffer();
            indices = x.next();
            for (int i = 0; i < indices.length; i++) {
                permutation.append(elements[indices[i]]);
            }
            System.out.println(permutation.toString());
        }
    }
}

/* */
