package org.jing.core.logger;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jing.core.lang.Configuration;
import org.jing.core.logger.log4j.Log4jLoggerLevel;
import org.jing.core.util.StringUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-12-08 <br>
 */
public class JingLogger {
    private Logger logger;

    private static boolean initFlag = false;

    private JingLogger() {
        if (!Configuration.hasInit()) {
            Configuration.getInstance();
        }
    }

    public static JingLogger getLogger(String name) {
        JingLogger retLogger = new JingLogger();
        retLogger.logger = Logger.getLogger(name);
        return retLogger;
    }

    public static JingLogger getLogger(Class clazz) {
        JingLogger retLogger = new JingLogger();
        retLogger.logger = Logger.getLogger(clazz);
        return retLogger;
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void info(Object object) {
        logger.info(object);
    }

    public void info(Throwable throwable) {
        logger.info(throwable);
    }

    public void info(String msg, String... parameters) {
        logger.info(StringUtil.mixParameters(msg, parameters));
    }

    public void info(String msg, Object... parameters) {
        logger.info(StringUtil.mixParameters(msg, parameters));
    }

    public void info(String msg, Throwable throwable) {
        logger.info(msg+ "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void info(String msg, Throwable throwable, String... parameters) {
        logger.info(StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void info(String msg, Throwable throwable, Object... parameters) {
        logger.info(StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void debug(String msg) {
        logger.debug(msg);
    }

    public void debug(Object object) {
        logger.debug(object);
    }

    public void debug(Throwable throwable) {
        logger.debug(throwable);
    }

    public void debug(String msg, String... parameters) {
        logger.debug(StringUtil.mixParameters(msg, parameters));
    }

    public void debug(String msg, Object... parameters) {
        logger.debug(StringUtil.mixParameters(msg, parameters));
    }

    public void debug(String msg, Throwable throwable) {
        logger.debug(msg+ "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void debug(String msg, Throwable throwable, String... parameters) {
        logger.debug(StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void debug(String msg, Throwable throwable, Object... parameters) {
        logger.debug(StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void warn(String msg) {
        logger.warn(msg);
    }

    public void warn(Object object) {
        logger.warn(object);
    }

    public void warn(Throwable throwable) {
        logger.warn(throwable);
    }

    public void warn(String msg, String... parameters) {
        logger.warn(StringUtil.mixParameters(msg, parameters));
    }

    public void warn(String msg, Object... parameters) {
        logger.warn(StringUtil.mixParameters(msg, parameters));
    }

    public void warn(String msg, Throwable throwable) {
        logger.warn(msg+ "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void warn(String msg, Throwable throwable, String... parameters) {
        logger.warn(StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void warn(String msg, Throwable throwable, Object... parameters) {
        logger.warn(StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void imp(String msg) {
        logger.log(Log4jLoggerLevel.IMP, msg);
    }

    public void imp(Object object) {
        logger.log(Log4jLoggerLevel.IMP, object);
    }

    public void imp(Throwable throwable) {
        logger.log(Log4jLoggerLevel.IMP, "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void imp(String msg, String... parameters) {
        logger.log(Log4jLoggerLevel.IMP, StringUtil.mixParameters(msg, parameters));
    }

    public void imp(String msg, Object... parameters) {
        logger.log(Log4jLoggerLevel.IMP, StringUtil.mixParameters(msg, parameters));
    }

    public void imp(String msg, Throwable throwable) {
        logger.log(Log4jLoggerLevel.IMP, msg+ "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void imp(String msg, Throwable throwable, String... parameters) {
        logger.log(Log4jLoggerLevel.IMP, StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void imp(String msg, Throwable throwable, Object... parameters) {
        logger.log(Log4jLoggerLevel.IMP, StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void sql(String msg, int session) {
        logger.log(Log4jLoggerLevel.SQL, String.format("[Session: %s]\r\n%s", session, msg));
    }

    public void sql(String msg, String parameters, int session) {
        logger.log(Log4jLoggerLevel.SQL, String.format("[Session: %s]\r\n%s[%s]", session, msg, parameters));
    }

    public void sql(String msg, int session, Object... parameters) {
        logger.log(Log4jLoggerLevel.SQL, StringUtil.mixParameters(String.format("[Session: %s]\r\n%s", session, msg), parameters));
    }

    public void error(String msg) {
        logger.error(msg);
    }

    public void error(Object object) {
        logger.error(object);
    }

    public void error(Throwable throwable) {
        logger.error("\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void error(String msg, String... parameters) {
        logger.error(StringUtil.mixParameters(msg, parameters));
    }

    public void error(String msg, Object... parameters) {
        logger.error(StringUtil.mixParameters(msg, parameters));
    }

    public void error(String msg, Throwable throwable) {
        logger.error(msg + "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void error(String msg, Throwable throwable, String... parameters) {
        logger.error(StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void error(String msg, Throwable throwable, Object... parameters) {
        logger.error(StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void log(Level level, String msg) {
        logger.log(level, msg);
    }

    public void log(Level level, Object object) {
        logger.log(level, object);
    }

    public void log(Level level, Throwable throwable) {
        logger.log(level, "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void log(Level level, String msg, String... parameters) {
        logger.log(level, StringUtil.mixParameters(msg, parameters));
    }

    public void log(Level level, String msg, Object... parameters) {
        logger.log(level, StringUtil.mixParameters(msg, parameters));
    }

    public void log(Level level, String msg, Throwable throwable) {
        logger.log(level, msg+ "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void log(Level level, String msg, Throwable throwable, String... parameters) {
        logger.log(level, StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public void log(Level level, String msg, Throwable throwable, Object... parameters) {
        logger.log(level, StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
    }

    public static void setInitFlag() {
        initFlag = true;
    }

    public static boolean hasInit() {
        return initFlag;
    }
}
