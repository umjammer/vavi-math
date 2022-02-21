/*
 * Copyright (c) 2020 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;


/**
 * GeneratorTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2020/11/12 umjammer initial version <br>
 */
class GeneratorTest {

    @Test
    void test() {
        List<Integer> actual = new ArrayList<>();
        Generator<Integer> generator = new Generator<Integer>() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    yield(i);
                }
            }
        };
        generator.forEach(actual::add);
        assertIterableEquals(IntStream.range(0, 100).mapToObj(Integer::valueOf).collect(Collectors.toList()), actual);
    }

    @Test
    void test2() {
        Generator<Integer> fibonacci = new Generator<Integer>() {
            @Override
            public void run() {
                int a = 0, b = 1;
                while (true) {
                    a = b + (b = a);
                    yield(a);
                }
            }
        };

        for (int x : fibonacci) {
            if (x > 2000000)
                break;
            System.out.println(x);
        }
    }
}

/* */
