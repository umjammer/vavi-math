/*
 * Copyright (c) 2012 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * StringUtil.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2012/11/01 umjammer initial version <br>
 */
public abstract class StringUtil {

    /** */
    private static final Map<Character, Character> subscriptMap = new HashMap<Character, Character>() {
        {
            put('0', '\u2080');
            put('1', '\u2081');
            put('2', '\u2082');
            put('3', '\u2083');
            put('4', '\u2084');
            put('5', '\u2085');
            put('6', '\u2086');
            put('7', '\u2087');
            put('8', '\u2088');
            put('9', '\u2089');
        }
    };

    /** */
    public static String toSubscript(int i) {
        StringBuilder sb = new StringBuilder();
        for (char c : String.valueOf(i).toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(subscriptMap.get(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /** */
    public static <T> String toSequence(Iterable<T> i) {
        StringBuilder sb = new StringBuilder("[");
        for (T e : i) {
            sb.append(e + ", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }

    /** */
    public static <T> String toSequence(T[] i) {
        StringBuilder sb = new StringBuilder("[");
        for (T e : i) {
            sb.append(e + ", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }

    /** */
    public static String toSequence(int[] i) {
        StringBuilder sb = new StringBuilder("[");
        for (int e : i) {
            sb.append(e + ", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }

    /** */
    public static String toSequence(long[] i) {
        StringBuilder sb = new StringBuilder("[");
        for (long e : i) {
            sb.append(e + ", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }

    /** */
    public static List<Integer> toIntList(String s) {
        s = s.replaceAll("[\\[\\] ]", "");
        String[] a = s.split(",");
        List<Integer> l = new ArrayList<>();
        for (String e : a) {
            l.add(Integer.valueOf(e));
        }
        return l;
    }

    /** */
    public static List<Boolean> toBooleanList(String s) {
        s = s.replaceAll("[\\[\\] ]", "");
        String[] a = s.split(",");
        List<Boolean> l = new ArrayList<>();
        for (String e : a) {
            l.add(Boolean.valueOf(e));
        }
        return l;
    }

    private static final String[] underTwenties = {
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
        "ten",
        "eleven",
        "twelve",
        "thirteen",
        "fourteen",
        "fifteen",
        "sixteen",
        "seventeen",
        "eighteen",
        "nineteen",
    };

    private static final String[] multiplesOfTen = {
        "twenty",
        "thirty",
        "forty",
        "fifty",
        "sixty",
        "seventy",
        "eighty",
        "ninety",
    };

    /**
     * TODO use locale
     * @param i <= 1000
     * @see "https://stackoverflow.com/questions/3911966/how-to-convert-number-to-words-in-java"
     */
    public static String formatInBritishEnglish(int i) {
        if (i < 20) {
            return underTwenties[i - 1];
        } else if (i < 100) {
            return multiplesOfTen[i / 10 - 2] + ((i % 10 != 0) ? "-" + underTwenties[i % 10 - 1] : "");
        } else if (i < 1000) {
            return underTwenties[i / 100 - 1] + " " + "hundred" + ((i % 100 != 0) ? " " + "and" + " " + formatInBritishEnglish(i % 100) : "");
        } else {
            return underTwenties[i / 1000 - 1] + " " + "thousand";
        }
    }

    /** */
    public static boolean isPalindrome(String s) {
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }
}

/* */
