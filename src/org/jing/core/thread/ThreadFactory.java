package org.jing.core.thread;

import org.jing.core.lang.ExceptionHandler;
import org.jing.core.lang.JingException;
import org.jing.core.logger.JingLogger;
import org.jing.core.util.StringUtil;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class ThreadFactory implements Runnable {
    private static final JingLogger LOGGER = JingLogger.getLogger(ThreadFactory.class);

    private static int maxThreadNumber = 16;

    private static final HashSet<ThreadFactory> runningSet = new HashSet<ThreadFactory>();

    private static final LinkedList<ThreadFactory> waitingList = new LinkedList<ThreadFactory>();

    private Object incident;

    private String methodName;

    private boolean forceAccess;

    private ThreadFactory(Object incident, String methodName, boolean forceAccess) throws JingException {
        if (null == incident) {
            ExceptionHandler.publish("Incident cannot be empty.");
        }
        if (StringUtil.isEmpty(methodName)) {
            ExceptionHandler.publish("MethodName cannot be empty.");
        }
        this.incident = incident;
        this.methodName = methodName;
        this.forceAccess = forceAccess;
    }

    private static boolean checkAvailable() {
        synchronized (ThreadFactory.class) {
            return runningSet.size() < maxThreadNumber;
        }
    }

    private static int getAvailableCount() {
        synchronized (ThreadFactory.class) {
            if (checkAvailable()) {
                synchronized (ThreadFactory.class) {
                    return maxThreadNumber - runningSet.size();
                }
            }
            else {
                return -1;
            }
        }
    }

    private static boolean addThread(ThreadFactory threadFactory) {
        synchronized (ThreadFactory.class) {
            if (checkAvailable()) {
                synchronized (ThreadFactory.class) {
                    if (checkAvailable()) {
                        runningSet.add(threadFactory);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void removeThread(ThreadFactory threadFactory) {
        synchronized (ThreadFactory.class) {
            runningSet.remove(threadFactory);
        }
    }

    private static boolean checkWaitingEmpty() {
        synchronized (ThreadFactory.class) {
            return waitingList.isEmpty();
        }
    }

    private static void addWaiting(ThreadFactory threadFactory) {
        synchronized (ThreadFactory.class) {
            waitingList.addLast(threadFactory);
        }
    }

    private static ThreadFactory getFirstWaiting() {
        synchronized (ThreadFactory.class) {
            return waitingList.getFirst();
        }
    }

    private static ThreadFactory removeFirstWaiting() {
        synchronized (ThreadFactory.class) {
            return waitingList.removeFirst();
        }
    }

    public static boolean setMaxThreadNumber(int count) {
        synchronized (ThreadFactory.class) {
            if (count > maxThreadNumber || getAvailableCount() > 0) {
                maxThreadNumber = count;
                notifyAvailableThread();
                return true;
            }
            else {
                return false;
            }
        }
    }

    public static void createThread(Object incident, String methodName, boolean forceAccess, boolean wait) throws JingException {
        ThreadFactory thread = new ThreadFactory(incident, methodName, forceAccess);
        if (checkAvailable() && addThread(thread)) {
            new Thread(thread).start();
        }
        else if (wait) {
            LOGGER.imp("No available thread. [incident: {}]", incident.getClass().getName());
            LOGGER.imp("Try to wait. [incident: {}]", incident.getClass().getName());
            addWaiting(thread);
        }
        else {
            LOGGER.imp("No available thread for no wait. [incident: {}]", incident.getClass().getName());
        }
    }

    private static void notifyAvailableThread() {
        synchronized (ThreadFactory.class) {
            if (checkAvailable() && !checkWaitingEmpty()) {
                ThreadFactory nextThread = getFirstWaiting();
                if (addThread(nextThread)) {
                    new Thread(removeFirstWaiting()).start();
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            Method method = incident.getClass().getDeclaredMethod(methodName);
            if (forceAccess) {
                method.setAccessible(true);
            }
            method.invoke(incident);
        }
        catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            removeThread(this);
            notifyAvailableThread();
        }
    }
}
