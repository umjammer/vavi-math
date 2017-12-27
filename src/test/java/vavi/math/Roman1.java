/*
 * Copyright (c) MMVII by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math;


/**
 * Integer to Roman converter.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 MMVII-IV-X nsano initial version <br>
 */
@Deprecated
class Roman1 {
    @Deprecated
    public static String toRoman(int n) {
        String roman = "";
        if (n >= 889) {
            roman = "M" + toRoman((n - 1000));
        } else if (n >= 389) {
            roman = "D" + toRoman((n - 500));
        } else if (n >= 89) {
            roman = "C" + toRoman((n - 100));
        } else if (n >= 39) {
            roman = "L" + toRoman((n - 50));
        } else if (n >= 9) {
            roman = "X" + toRoman((n - 10));
        } else if (n >= 4) {
            roman = "V" + toRoman((n - 5));
        } else if (n >= 1) {
            roman = "I" + toRoman((n - 1));
        } else if (n <= -889) {
            roman = "M" + toRoman(n + 1000);
        } else if (n <= -389) {
            roman = "D" + toRoman(n + 500);
        } else if (n <= -89) {
            roman = "C" + toRoman(n + 100);
        } else if (n <= -39) {
            roman = "L" + toRoman(n + 50);
        } else if (n <= -9) {
            roman = "X" + toRoman(n + 10);
        } else if (n <= -4) {
            roman = "V" + toRoman(n + 5);
        } else if (n <= -1) {
            roman = "I" + toRoman(n + 1);
        }
        return roman;
    }
}
/* */
