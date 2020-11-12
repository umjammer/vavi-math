/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util;

import org.junit.jupiter.api.Test;


/**
 * GeneratorTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2020/11/12 umjammer initial version <br>
 */
class GeneratorTest {

    @Test
    void test() {
        Generator<Integer> generator = new Generator<Integer>() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    yield(i);
                }
            }
        };
        generator.forEach(System.err::print);
        System.err.println();
    }
}

/* */
