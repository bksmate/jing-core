package org.jing.core.thread;

import org.jing.core.lang.ExceptionHandler;
import org.jing.core.lang.JingException;
import org.jing.core.lang.Pair2;
import org.jing.core.logger.JingLogger;
import org.jing.core.util.GenericUtil;
import org.jing.core.util.StringUtil;

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

    private Class<?> type;

    private Object incident;

    private boolean forceAccess;

    private Constructor constructor;

    private Method method;

    public static class Constructor {
        private Class<?>[] constructorTypes;

        private Object[] constructorValues;

        public Constructor(Pair2<Class<?>, ?>... constructorParameters) {
            int size = GenericUtil.countArray(constructorParameters);
            if (0 != size) {
                this.constructorTypes = new Class[size];
                this.constructorValues = new Object[size];
                for (int i$ = 0; i$ < size; i$++) {
                    this.constructorTypes[i$] = constructorParameters[i$].getA();
                    this.constructorValues[i$] = constructorParameters[i$].getB();
                }
            }
            else {
                this.constructorTypes = null;
                this.constructorValues = null;
            }
        }

        public Class<?>[] getConstructorTypes() {
            return constructorTypes;
        }

        public Object[] getConstructorValues() {
            return constructorValues;
        }
    }

    public static class Method {
        private String methodName;

        private Class<?>[] methodTypes;

        private Object[] methodValues;

        public Method(String methodName, Pair2<Class<?>, ?>... methodParameters) {
            this.methodName = methodName;
            int size = GenericUtil.countArray(methodParameters);
            if (0 != size) {
                this.methodTypes = new Class[size];
                this.methodValues = new Object[size];
                for (int i$ = 0; i$ < size; i$++) {
                    this.methodTypes[i$] = methodParameters[i$].getA();
                    this.methodValues[i$] = methodParameters[i$].getB();
                }
            }
            else {
                this.methodTypes = null;
                this.methodValues = null;
            }
        }

        public String getMethodName() {
            return methodName;
        }

        public Class<?>[] getMethodTypes() {
            return methodTypes;
        }

        public Object[] getMethodValues() {
            return methodValues;
        }
    }

    private ThreadFactory() {

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

    public static void createThreadByIncident(Object incident, Method method, boolean forceAccess, boolean wait) throws JingException {
        ThreadFactory thread = new ThreadFactory();
        if (null == incident) {
            ExceptionHandler.publish("Incident cannot be empty.");
            return;
        }
        if (null == method || StringUtil.isEmpty(method.getMethodName())) {
            ExceptionHandler.publish("Method cannot be empty.");
        }
        thread.type = incident.getClass();
        thread.incident = incident;
        thread.constructor = null;
        thread.method = method;
        thread.forceAccess = forceAccess;
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

    public static void createThreadByType(Class<?> type, Constructor constructor, Method method, boolean forceAccess, boolean wait) throws JingException {
        ThreadFactory thread = new ThreadFactory();
        if (null == type) {
            ExceptionHandler.publish("Type cannot be empty.");
            return;
        }
        thread.type = type;
        thread.incident = null;
        thread.constructor = constructor;
        thread.method = method;
        thread.forceAccess = forceAccess;
        if (checkAvailable() && addThread(thread)) {
            new Thread(thread).start();
        }
        else if (wait) {
            LOGGER.imp("No available thread. [type: {}]", type.getName());
            LOGGER.imp("Try to wait. [type: {}]", type.getName());
            addWaiting(thread);
        }
        else {
            LOGGER.imp("No available thread for no wait. [type: {}]", type.getName());
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
            Object incident;
            if (null == this.incident) {
                if (null != this.constructor) {
                    java.lang.reflect.Constructor constructor = this.type.getConstructor(this.constructor.getConstructorTypes());
                    if (forceAccess) {
                        constructor.setAccessible(true);
                    }
                    incident = constructor.newInstance(this.constructor.getConstructorValues());
                }
                else {
                    incident = this.type.newInstance();
                }
            }
            else {
                incident = this.incident;
            }
            if (null != this.method) {
                java.lang.reflect.Method method = incident.getClass().getDeclaredMethod(this.method.getMethodName(), this.method.getMethodTypes());
                if (forceAccess) {
                    method.setAccessible(true);
                }
                method.invoke(incident, this.method.getMethodValues());
            }
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
