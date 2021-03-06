/*
 * Copyright (c) 2013 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * SoundexTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2013/01/23 umjammer initial version <br>
 */
public class SoundexTest {

    @Test
    public void test() {
        main(new String[] {});
        assertTrue(true);
    }

    /** main */
    public static void main(String[] args) {
        String[] names = {
            "Darwin, Ian", "Davidson, Greg", "Darwent, William", "Derwin, Daemon"
        };
        for (int i = 0; i < names.length; i++)
            System.out.println(Soundex.soundex(names[i]) + ' ' + names[i]);
    }
}

/* */
