package org.jing.core.logger;

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
        logger.info(msg, throwable);
    }

    public void info(String msg, Throwable throwable, String... parameters) {
        logger.info(StringUtil.mixParameters(msg, parameters), throwable);
    }

    public void info(String msg, Throwable throwable, Object... parameters) {
        logger.info(StringUtil.mixParameters(msg, parameters), throwable);
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
        logger.debug(msg, throwable);
    }

    public void debug(String msg, Throwable throwable, String... parameters) {
        logger.debug(StringUtil.mixParameters(msg, parameters), throwable);
    }

    public void debug(String msg, Throwable throwable, Object... parameters) {
        logger.debug(StringUtil.mixParameters(msg, parameters), throwable);
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
        logger.warn(msg, throwable);
    }

    public void warn(String msg, Throwable throwable, String... parameters) {
        logger.warn(StringUtil.mixParameters(msg, parameters), throwable);
    }

    public void warn(String msg, Throwable throwable, Object... parameters) {
        logger.warn(StringUtil.mixParameters(msg, parameters), throwable);
    }

    public void imp(String msg) {
        logger.log(Log4jLoggerLevel.IMP, msg);
    }

    public void imp(Object object) {
        logger.log(Log4jLoggerLevel.IMP, object);
    }

    public void imp(Throwable throwable) {
        logger.log(Log4jLoggerLevel.IMP, throwable);
    }

    public void imp(String msg, String... parameters) {
        logger.log(Log4jLoggerLevel.IMP, StringUtil.mixParameters(msg, parameters));
    }

    public void imp(String msg, Object... parameters) {
        logger.log(Log4jLoggerLevel.IMP, StringUtil.mixParameters(msg, parameters));
    }

    public void imp(String msg, Throwable throwable) {
        logger.log(Log4jLoggerLevel.IMP, msg, throwable);
    }

    public void imp(String msg, Throwable throwable, String... parameters) {
        logger.log(Log4jLoggerLevel.IMP, StringUtil.mixParameters(msg, parameters), throwable);
    }

    public void imp(String msg, Throwable throwable, Object... parameters) {
        logger.log(Log4jLoggerLevel.IMP, StringUtil.mixParameters(msg, parameters), throwable);
    }

    public void error(String msg) {
        logger.error(msg);
    }

    public void error(Object object) {
        logger.error(object);
    }

    public void error(Throwable throwable) {
        logger.error(throwable);
    }

    public void error(String msg, String... parameters) {
        logger.error(StringUtil.mixParameters(msg, parameters));
    }

    public void error(String msg, Object... parameters) {
        logger.error(StringUtil.mixParameters(msg, parameters));
    }

    public void error(String msg, Throwable throwable) {
        logger.error(msg, throwable);
    }

    public void error(String msg, Throwable throwable, String... parameters) {
        logger.error(StringUtil.mixParameters(msg, parameters), throwable);
    }

    public void error(String msg, Throwable throwable, Object... parameters) {
        logger.error(StringUtil.mixParameters(msg, parameters), throwable);
    }

    public static void setInitFlag() {
        initFlag = true;
    }

    public static boolean hasInit() {
        return initFlag;
    }
}
