
package vavi.util;

import org.junit.jupiter.api.Test;


public class CoMain {

    Coroutine c;

    @Test
    void test1() {
        init();
        while (c.yield())
            ;
    }

    class Test1 implements Runnable {
        int p;

        Test1(int p) {
            this.p = p;
        }

        public void run() {
            for (int i = 0; i < 5; ++i) {
                c.yield();
                System.err.printf("[" + p + "] - " + i + " => sleep(5) => ");
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    System.err.printf("interrupted => ");
                }
                System.err.println( "[" + p + "] - " + i);
            }
            System.err.println("[" + p + "] finished");
        }
    }

    void init() {
        c = new Coroutine();
        for (int i = 0; i < 5; ++i) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
            c.add(new Test1(i));
        }
    }
}
