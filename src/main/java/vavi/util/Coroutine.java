/*
 * http://d.hatena.ne.jp/lethevert/20070621/p3
 */

package vavi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Coroutine {

    private final ArrayList<Thread> pool = new ArrayList<>();
    private final HashMap<Thread, Integer> dict = new HashMap<>();
    private final Random rand = new Random();
    private final Object mutex = new Object();
    private volatile int currentId;

    public Coroutine() {
        Thread t = Thread.currentThread();
        currentId = 0;
        pool.add(t);
        dict.put(t, currentId);
    }

    public int add(final Runnable c) {
        final int myId;
        synchronized (mutex) {
            if (!pool.get(currentId).equals(Thread.currentThread()))
                throw new IllegalStateException();
            myId = currentId;
        }
        final int id = pool.size();
        Thread t = new Thread() {
            public void run() {
                yieldTo(myId);
                c.run();
                synchronized (mutex) {
                    pool.set(id, null);
                    int nid = getNextThread(id);
                    if (nid >= 0) {
                        currentId = nid;
                        mutex.notifyAll();
                    }
                }
            }
        };
        pool.add(t);
        dict.put(t, id);
        synchronized (mutex) {
            t.start();
            yieldTo(id);
        }
        return id;
    }

    private int getNextThread(final int myId) {
        final int iid;
        int id = rand.nextInt(pool.size());
        if (id == myId) {
            ++id;
            if (id >= pool.size())
                id = 0;
        }
        iid = id;
        while (id == myId || pool.get(id) == null) {
            ++id;
            if (id >= pool.size())
                id = 0;
            if (id == iid)
                return -1;
        }
        return id;
    }

    public boolean yield() {
        synchronized (mutex) {
            if (!pool.get(currentId).equals(Thread.currentThread()))
                throw new IllegalStateException();
            final int myId = currentId;
            final int id = getNextThread(myId);
            if (id < 0)
                return false;
            currentId = id;
            mutex.notifyAll();
            while (myId != currentId)
                try {
                    mutex.wait(100);
                } catch (InterruptedException e) {
                }
        }
        return true;
    }

    public boolean yieldTo(final int id) {
        if (id < 0 || pool.size() <= id)
            throw new IndexOutOfBoundsException();
        synchronized (mutex) {
            if (!pool.get(currentId).equals(Thread.currentThread()))
                throw new IllegalStateException();
            final int myId = currentId;
            final Thread t = pool.get(id);
            if (null != t) {
                currentId = id;
                mutex.notifyAll();
                while (myId != currentId)
                    try {
                        mutex.wait(100);
                    } catch (InterruptedException e) {
                    }
                return true;
            } else {
                return false;
            }
        }
    }
}
