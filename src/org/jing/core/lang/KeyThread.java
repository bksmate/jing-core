package org.jing.core.lang;

import java.util.HashMap;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-01-05 <br>
 */
@Deprecated
public class KeyThread {
    public enum ThreadName {
        MAIN;

        private ThreadName() {
        }
    }

    private static final HashMap<ThreadName, Thread> THREAD_MAP = new HashMap<>();

    public synchronized boolean checkThreadAlive(ThreadName threadName) {
        Thread thread = THREAD_MAP.get(threadName);
        if (null == thread) {
            return false;
        }
        else {
            return thread.isAlive();
        }
    }

    public synchronized void putThread(ThreadName threadName, Thread thread) {
        THREAD_MAP.put(threadName, thread);
    }

    public synchronized void removeThread(ThreadName threadName) {
        THREAD_MAP.remove(threadName);
    }
}
