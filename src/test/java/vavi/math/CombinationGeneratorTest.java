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
import static vavi.math.StringUtil.toSubscript;


/**
 * CombinationGeneratorTest. 
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2012/11/01 umjammer initial version <br>
 */
public class CombinationGeneratorTest {

    @Test
    public void test() throws Exception {
        int n = 214;
        int r = 2;
        CombinationGenerator x = new CombinationGenerator(n, r);
        assertEquals(BigInteger.valueOf(22791), x.getTotal());
        Scanner scanner = new Scanner(CombinationGeneratorTest.class.getResourceAsStream("/vavi/math/cg.dat"));
        while (x.hasNext()) {
            int[] is = x.next();
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            assertEquals(a, is[0]);
            assertEquals(b, is[1]);
        }
        scanner.close();
    }

    @Test
    public void test2() throws Exception {
        int n = 214;
        int r = 2;
        CombinationGenerator x = new CombinationGenerator(n, r);
        assertEquals(BigInteger.valueOf(22791), x.getTotal());
        Scanner scanner = new Scanner(CombinationGeneratorTest.class.getResourceAsStream("/vavi/math/cg.dat"));
        for (int[] is : x) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            assertEquals(a, is[0]);
            assertEquals(b, is[1]);
        }
        scanner.close();
    }

    /** */
    public static void main(String[] args) throws Exception {
        int n = 214;
        int r = 2;
        CombinationGenerator x = new CombinationGenerator(n, r);
        System.out.printf("%sC%s = %d\n", toSubscript(n), toSubscript(r), x.getTotal());
        while (x.hasNext()) {
            int[] is = x.next();
            System.out.printf(" %d %d\n", is[0], is[1]);
        }
    }
}

/* */
