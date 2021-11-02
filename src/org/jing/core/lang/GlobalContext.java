package org.jing.core.lang;

import javafx.util.Pair;
import org.jing.core.lang.annotation.Getter;
import org.jing.core.lang.annotation.Setter;
import org.jing.core.logger.JingLogger;
import org.jing.core.util.GenericUtil;
import org.jing.core.util.StringUtil;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-22 <br>
 */
public final class GlobalContext implements Closeable {
    private static final JingLogger LOGGER = JingLogger.getLogger(GlobalContext.class);

    private static final ThreadLocal<GlobalContext> CONTEXT = new ThreadLocal<>();

    private static final HashMap<Class, Object> MAP = new HashMap<>();

    private static InterfaceHandler handler = new InterfaceHandler();

    private Carrier paramC = new Carrier();

    private static Closeable getCloseable(String path) {
        GlobalContext context = getCurrentGlobalContext();
        Closeable closeable = (Closeable) context.paramC.getValueByPath(path);
        if (null == closeable) {
            throw new JingRuntimeException("{} has not initialized", path);
        }
        return closeable;
    }

    private static void addCloseable(String path, Closeable closeable) throws JingException {
        // 获取当前线程的context
        GlobalContext context = getCurrentGlobalContext();
        if (null != context.paramC.getValueByPath(path)) {
            throw new JingException("cannot reset the global value");
        }
        context.paramC.setValueByPath(path, closeable);
    }

    private GlobalContext() {
    }

    private static GlobalContext getCurrentGlobalContext() {
        // 获取当前线程的context
        GlobalContext context = CONTEXT.get();
        if (null == context) {
            context = new GlobalContext();
            CONTEXT.set(context);
        }
        return context;
    }

    public static GlobalContext port() {
        return getCurrentGlobalContext();
    }

    @SuppressWarnings("unchecked")
    public static <T> T map(Class<T> clazz) {
        synchronized (GlobalContext.class) {
            T instance = (T) MAP.get(clazz);
            if (null == instance) {
                instance = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, handler);
                MAP.put(clazz, instance);
            }
            return instance;
        }
    }

    private static class InterfaceHandler implements InvocationHandler {
        private static boolean init = false;

        private InterfaceHandler() {
            if (init) {
                throw new JingRuntimeException("handler cannot be initialized twice");
            }
            init = true;
        }

        @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                Getter getter = method.getAnnotation(Getter.class);
                Setter setter = method.getAnnotation(Setter.class);
                if (null != getter) {
                    String path = getter.value();
                    if (StringUtil.isEmpty(path)) {
                        throw new JingException("empty getter property annotation");
                    }
                    return GlobalContext.getCloseable(path);
                }
                else if (null != setter) {
                    String path = setter.value();
                    if (StringUtil.isEmpty(path)) {
                        throw new JingException("empty setter property annotation");
                    }
                    if (null == args || args.length < 1) {
                        throw new JingException("no Closeable to be add");
                    }
                    if (null == args[0]) {
                        throw new JingException("null Closeable to be add");
                    }
                    if (!(args[0] instanceof Closeable)) {
                        throw new JingException("the 1st parameter must be Closeable");
                    }
                    LOGGER.debug("add global context: {}", path);
                    GlobalContext.addCloseable(path, (Closeable) args[0]);
                    return null;
                }
                else {
                    throw new JingException("not getter or setter defined in method: {}", method.getName());
                }
            }
            catch (Throwable t) {
                throw new JingException(t);
            }
        }
    }

    @Override public void close() throws IOException {
        synchronized (GlobalContext.class) {
            List<Pair2<String, Object>> childList = paramC.getValueChildList();
            Pair2<String, Object> pair;
            int size = GenericUtil.countList(childList);
            for (int i$ = size - 1; i$ >= 0; i$--) {
                pair = childList.get(i$);
                LOGGER.info("trigger close for {}", pair.getA());
                ((Closeable) pair.getB()).close();
                paramC.getChildList().clear();
            }
        }
    }
}
