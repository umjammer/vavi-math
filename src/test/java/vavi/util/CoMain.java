
package vavi.util;

public class CoMain {
    static Coroutine c;

    public static void main(String[] args) {
        init();
        while (c.yield())
            ;
        System.out.println("main finished");
    }

    static class Test implements Runnable {
        int p;

        Test(int p) {
            this.p = p;
        }

        public void run() {
            for (int i = 0; i < 5; ++i) {
                c.yield();
                System.out.print("[" + p + "] - " + i + " => sleep(5) => ");
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    System.out.print("interrupted => ");
                }
                System.out.println("[" + p + "] - " + i);
            }
            System.out.println("[" + p + "] finished");
        }
    }

    static void init() {
        c = new Coroutine();
        for (int i = 0; i < 5; ++i)
            c.add(new Test(i));
    }
}
