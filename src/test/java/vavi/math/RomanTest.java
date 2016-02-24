/*
 * Copyright (c) MMVII by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math;


/**
 * Integer to Roman converter.
 * 
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.00 MMVII-IV-X nsano initial version <br>
 */
public class RomanTest {

    /** */
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        int i = 1970;
        System.err.println("Roman1: " + i + ": " + Roman1.toRoman(i));
        System.err.println("Roman : " + i + ": " + Roman.toRoman(i));
        System.err.println("Roman2: " + i + ": " + Roman2.toRoman(i));
        i = 2007;
        System.err.println("Roman : " + i + ": " + Roman.toRoman(i));
        System.err.println("Roman2: " + i + ": " + Roman2.toRoman(i));
        i = 4;
        System.err.println("Roman : " + i + ": " + Roman.toRoman(i));
        i = 10;
        System.err.println("Roman : " + i + ": " + Roman.toRoman(i));

        String r = "MMXXX";
        System.err.println("Roman2: " + r + ": " + Roman2.toInt(r));
        r = "MCMLXX";
        System.err.println("Roman2: " + r + ": " + Roman2.toInt(r));
        r = "MDCCCCLXX";
        System.err.println("Roman2: " + r + ": " + Roman2.toInt(r));
        r = "MMVII";
        System.err.println("Roman2: " + r + ": " + Roman2.toInt(r));
    }
}

/* */
