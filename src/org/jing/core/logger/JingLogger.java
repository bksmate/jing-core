package org.jing.core.logger;

import org.jing.core.lang.Configuration;
import org.jing.core.lang.JingException;
import org.jing.core.logger.itf.JingLoggerFactoryItf;
import org.jing.core.logger.itf.JingLoggerLevelItf;
import org.jing.core.logger.local.LocalLoggerLevel;

import java.util.Hashtable;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-16 <br>
 */
public abstract class JingLogger {
    private static JingLoggerFactoryItf factory;

    private static Hashtable<String, JingLogger> repository = new Hashtable<>();

    public synchronized static void setFactory(JingLoggerFactoryItf factory) {
        JingLogger.factory = factory;
    }

    public static JingLogger getLogger(String name, boolean remake) {
        Configuration.getInstance();
        JingLogger logger;
        if (remake || null == (logger = repository.get(name))) {
            logger = factory.getLogger(name);
            repository.put(name, logger);
        }
        return logger;
    }

    public static JingLogger getLogger(String name) {
        return getLogger(name, false);
    }

    public static JingLogger getLogger(Class clazz, boolean remake) {
        String name = clazz.getName();
        return getLogger(name, remake);
    }

    public static JingLogger getLogger(Class clazz) {
        String name = clazz.getName();
        return getLogger(name, false);
    }

    public abstract void all(Object object);

    public abstract void all(Throwable throwable);

    public abstract void all(String msg, Object... parameters);

    public abstract void all(Throwable throwable, String msg);

    public abstract void all(Throwable throwable, String msg, Object... parameters);

    public abstract void trace(Object object);

    public abstract void trace(Throwable throwable);

    public abstract void trace(String msg, Object... parameters);

    public abstract void trace(Throwable throwable, String msg);

    public abstract void trace(Throwable throwable, String msg, Object... parameters);

    public abstract void debug(Object object);

    public abstract void debug(Throwable throwable);

    public abstract void debug(String msg, Object... parameters);

    public abstract void debug(Throwable throwable, String msg);

    public abstract void debug(Throwable throwable, String msg, Object... parameters);

    public abstract void info(Object object);

    public abstract void info(Throwable throwable);

    public abstract void info(String msg, Object... parameters);

    public abstract void info(Throwable throwable, String msg);

    public abstract void info(Throwable throwable, String msg, Object... parameters);

    public abstract void warn(Object object);

    public abstract void warn(Throwable throwable);

    public abstract void warn(String msg, Object... parameters);

    public abstract void warn(Throwable throwable, String msg);

    public abstract void warn(Throwable throwable, String msg, Object... parameters);

    public abstract void imp(Object object);

    public abstract void imp(Throwable throwable);

    public abstract void imp(String msg, Object... parameters);

    public abstract void imp(Throwable throwable, String msg);

    public abstract void imp(Throwable throwable, String msg, Object... parameters);

    public abstract void error(Object object);

    public abstract void error(Throwable throwable);

    public abstract void error(String msg, Object... parameters);

    public abstract void error(Throwable throwable, String msg);

    public abstract void error(Throwable throwable, String msg, Object... parameters);

    public abstract void fatal(Object object);

    public abstract void fatal(Throwable throwable);

    public abstract void fatal(String msg, Object... parameters);

    public abstract void fatal(Throwable throwable, String msg);

    public abstract void fatal(Throwable throwable, String msg, Object... parameters);

    public abstract void log(JingLoggerLevelItf level, Object object);

    public abstract void log(JingLoggerLevelItf level, Throwable throwable);

    public abstract void log(JingLoggerLevelItf level, Throwable throwable, String msg);

    public abstract void log(JingLoggerLevelItf level, Throwable throwable, String msg, Object... parameters);

    public abstract void log(JingLoggerLevelItf level, String msg, Object... parameters);
}
