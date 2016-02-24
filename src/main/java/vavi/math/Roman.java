/*
 * Copyright (c) MMVII by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math;


/**
 * A static method for converting binary integers to Roman numbers.
 * 
 * @author Fred Swartz 2006-12-29 Placed in public domain.
 */
class Roman {
    /**
     * This could be alternatively be done with parallel arrays. Another
     * alternative would be Pair<Integer, String>
     */
    private static final Roman.RomanValue[] ROMAN_VALUE_TABLE = {
        new RomanValue(1000, "M"),
        new RomanValue(900, "CM"),
        new RomanValue(500, "D"),
        new RomanValue(400, "CD"),
        new RomanValue(100, "C"),
        new RomanValue(90, "XC"),
        new RomanValue(50, "L"),
        new RomanValue(40, "XL"),
        new RomanValue(10, "X"),
        new RomanValue(9, "IX"),
        new RomanValue(5, "V"),
        new RomanValue(4, "IV"),
        new RomanValue(1, "I")
    };

    /** int2roman */
    public static String toRoman(int n) {
        if (n >= 4000 || n < 1) {
            throw new NumberFormatException("Numbers must be in range 1-3999");
        }
        StringBuilder result = new StringBuilder(10);

        // ... Start with largest value, and work toward smallest.
        for (Roman.RomanValue equiv : ROMAN_VALUE_TABLE) {
            // ... Remove as many of this value as possible (maybe none).
            while (n >= equiv.intValue) {
                n -= equiv.intValue; // Subtract value.
                result.append(equiv.romanValue); // Add roman equivalent.
            }
        }
        return result.toString();
    }

    /** inner value class */
    private static class RomanValue {
        /** Integer value. */
        int intValue;

        /** Equivalent roman numeral. */
        String romanValue;

        /** constructor */
        RomanValue(int dec, String rom) {
            this.intValue = dec;
            this.romanValue = rom;
        }
    }
}
/* */
