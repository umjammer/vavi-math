/*
 * Copyright (c) 2013 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util.memoization;

import org.junit.Test;


/**
 * MemoizeTest. 
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2013/03/05 umjammer initial version <br>
 */
public class MemoizeTest {

    @Test
    public void test() {
        m1();
    }

    @Memoize
    static int m1() {
        return 0;
    }
}

/* */
