/*
 * Copyright (c) 2013 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


/**
 * SoundexTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2013/01/23 umjammer initial version <br>
 */
public class SoundexTest {

    static final String[] names = {
        "Darwin, Ian", "Davidson, Greg", "Darwent, William", "Derwin, Daemon"
    };

    static final String[] soundexes = {
        "D650", "D132", "D653", "D650"
    };

    @Test
    public void test() {
        assertArrayEquals(soundexes, Arrays.stream(names).map(Soundex::soundex).toArray());
    }

    /** main */
    public static void main(String[] args) {
        for (int i = 0; i < names.length; i++)
            System.out.println(Soundex.soundex(names[i]) + ' ' + names[i]);
    }
}

/* */
