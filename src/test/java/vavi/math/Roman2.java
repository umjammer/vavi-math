/*
 * Copyright (c) MMVII by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math;


/**
 * Integer to Roman converter. max 4988
 * 
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.00 MMVII-IV-X nsano initial version <br>
 * @see "http://www.cppfrance.com/codes/VCPLUSPLUS-CONVERSION-ROMAIN-GT-DECIMAL-INVERSE_10812.aspx"
 */
class Roman2 {

    static class Work {
        char romanValue;
        int intValue;
        static final char[] table = {
            'M', 'D', 'C', 'L', 'X', 'V', 'I'
        };

        /* INITIALISATION S_ROMAN */
        static Work[] getInitial() {
            Roman2.Work[] work = new Roman2.Work[7];
            for (int j = 0; j < 7; j++) {
                work[j] = new Work();
                work[j].romanValue = table[j];
                work[j].intValue = 0;
            }
            return work;
        }
    }

    /** */
    static String toRoman(int number) {
        Roman2.Work[] work = Work.getInitial();
        String roman_nb = null;
        int i = 0;

        roman_nb = "";

        while (number - 1000 >= 0) {
            number = number - 1000;
            roman_nb += "M";
            i++;
//System.err.printf("roman_nb=%s nombre=%d i=%d\n", roman_nb, number, i);
        }

        while (number - 500 >= 0) {
            number = number - 500;
            roman_nb += "D";
            i++;
//System.err.printf("roman_nb=%s nombre=%d i=%d\n", roman_nb, number, i);
        }

        while (number - 100 >= 0) {
            number = number - 100;
            roman_nb += "C";
            i++;

//System.err.printf("roman_nb=%s nombre=%d i=%d\n", roman_nb, number, i);
        }

        while (number - 50 >= 0) {
            number = number - 50;
            roman_nb += "L";
            i++;

//System.err.printf("roman_nb=%s nombre=%d i=%d\n", roman_nb, number, i);
        }

        while (number - 10 >= 0) {
            number = number - 10;
            roman_nb += "X";
            i++;

            work[4].intValue++;

            if (work[4].intValue == 4) {
                roman_nb = roman_nb.substring(0, roman_nb.length() - 4);
                roman_nb += 'X';
                roman_nb += 'L';
                i = i - 2;
            }

//System.err.printf("roman_nb=%s nombre=%d i=%d\n", roman_nb, number, i);
        }

        while (number - 5 >= 0) {
            number = number - 5;
            roman_nb += "V";
            i++;
//System.err.printf("roman_nb=%s nombre=%d i=%d\n", roman_nb, number, i);
        }

        while (number - 1 >= 0) {
            number = number - 1;
            roman_nb += "I";
            i++;

            work[6].intValue++;

            if (work[6].intValue == 4) {
                roman_nb = roman_nb.substring(0, roman_nb.length() - 4);
                roman_nb += 'I';
                roman_nb += 'V';

                i = i - 2;

                if (roman_nb.charAt(i - 3) == 'V') {
                    roman_nb = roman_nb.substring(0, roman_nb.length() - 3);
                    roman_nb += 'I';
                    roman_nb += 'X';

                    i = i - 1;
                }
            }

//System.err.printf("roman_nb=%s nombre=%d i=%d\n", roman_nb, number, i);
        }

        return roman_nb;
    }

    /** */
    static int toInt(String roman_nb) {
        Roman2.Work[] work = Work.getInitial();
        int number = 0;
        int i = 0;

        while (i < roman_nb.length()) {
            if ((roman_nb.charAt(i) != 'M') && (roman_nb.charAt(i) != 'D') && (roman_nb.charAt(i) != 'C')
                && (roman_nb.charAt(i) != 'L') && (roman_nb.charAt(i) != 'X') && (roman_nb.charAt(i) != 'V')
                && (roman_nb.charAt(i) != 'I')) {
                return 0;
            }

            if (roman_nb.charAt(i) == 'M') {
                if (work[0].intValue == 4) {
                    return 0;
                }

                number = number + 1000;
                work[0].intValue++;
            }

            if (roman_nb.charAt(i) == 'D') {
                if (work[1].intValue == 1) {
                    return 0;
                }

                number = number + 500;
                work[1].intValue++;
            }

            if (roman_nb.charAt(i) == 'C') {
                if (work[2].intValue == 4) {
                    return 0;
                }

                number = number + 100;
                work[2].intValue++;
            }

            if (roman_nb.charAt(i) == 'L') {
                if (roman_nb.charAt(i - 1) == 'X') {
                    number = number - 10;
                    number = number + 40;
                    work[3].intValue++;
                } else {
                    if (work[3].intValue == 4) {
                        return 0;
                    }

                    number = number + 50;
                    work[3].intValue++;
                }
            }

            if (roman_nb.charAt(i) == 'X') {
                if (roman_nb.charAt(i - 1) == 'I') {
                    number = number - 1;
                    number = number + 9;
                    work[4].intValue++;
                } else {
                    if (work[4].intValue == 4) {
                        return 0;
                    }

                    number = number + 10;
                    work[4].intValue++;
                }
            }

            if (roman_nb.charAt(i) == 'V') {
                if (roman_nb.charAt(i - 1) == 'I') {
                    number = number - 1;
                    number = number + 4;
                    work[5].intValue++;
                } else {
                    if (work[5].intValue == 2) {
                        return 0;
                    }

                    number = number + 5;
                    work[5].intValue++;
                }
            }

            if (roman_nb.charAt(i) == 'I') {
                if (work[6].intValue == 4) {
                    return 0;
                }

                number = number + 1;
                work[6].intValue++;
            }

            i++;
        }

        return number;
    }
}
/* */
