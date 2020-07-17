package org.jing.core.thread;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.lang.Pair2;
import org.jing.core.lang.itf.JThread;
import org.jing.core.logger.JingLogger;
import org.jing.core.util.ClassUtil;

import java.util.HashMap;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-05-26 <br>
 */
public class MultiplyThread extends Thread{
    private static final JingLogger LOGGER = JingLogger.getLogger(MultiplyThread.class);

    private static final HashMap<Class<? super JThread>, MultiplyThread> THREAD_MAP = new HashMap<Class<? super JThread>, MultiplyThread>();

    private JThread threadInstance;

    private MultiplyThread(Class<? super JThread> threadClass, Carrier parameters) throws JingException {
        if (null == parameters) {
            threadInstance = (JThread) ClassUtil.createInstance(threadClass);
        }
        else {
            threadInstance = (JThread) ClassUtil.createInstance(threadClass, new Pair2<Class<?>, Object>(Carrier.class, parameters));
        }
        new Thread(threadInstance).start();
    }

    public static MultiplyThread createMultiplyThread(Class<? super JThread> threadClass, Carrier parameters) throws JingException {
        synchronized (MultiplyThread.class) {
            MultiplyThread thread = THREAD_MAP.get(threadClass);
            if (null == thread) {
                synchronized (MultiplyThread.class) {
                    thread = new MultiplyThread(threadClass, parameters);
                    THREAD_MAP.put(threadClass, thread);
                }
            }
            return thread;
        }
    }

    public JThread getThreadInstance() {
        return threadInstance;
    }
}
