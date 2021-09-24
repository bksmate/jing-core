package org.jing.core.lang;

import org.jing.core.logger.JingLogger;

import java.io.Closeable;
import java.io.IOException;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-22 <br>
 */
public class GlobalContext implements Closeable {
    private static final JingLogger LOGGER = JingLogger.getLogger(GlobalContext.class);

    private static final ThreadLocal<GlobalContext> GLOBAL_CONTEXT = new ThreadLocal<>();

    private Carrier paramC = new Carrier();

    protected static void addCloseable(String path, Closeable closeable) throws JingException {
        synchronized (GlobalContext.class) {
            // 获取当前线程的context
            GlobalContext context = GLOBAL_CONTEXT.get();
            if (null == context) {
                context = new GlobalContext();
                GLOBAL_CONTEXT.set(context);
            }
            if (null != context.paramC.getValueByPath(path)) {
                throw new JingException("cannot reset the global value");
            }
            context.paramC.setValueByPath(path, closeable);
        }
    }

    protected GlobalContext() {
    }

    @Override public void close() throws IOException {
        for (Pair2<String, Object> pair : paramC.getValueChildList()) {
            LOGGER.info("trigger close for {}", pair.getA());
            ((Closeable) pair.getB()).close();
        }
    }
}
