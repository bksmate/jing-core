package org.jing.core.logger.log4j;

import org.apache.log4j.Priority;
import org.jing.core.logger.JingLogger;
import org.jing.core.logger.itf.JingLoggerLevelItf;
import org.jing.core.util.StringUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-16 <br>
 */
public class Logger extends JingLogger {
    private org.apache.log4j.Logger logger;

    Logger(String name) {
        logger = org.apache.log4j.Logger.getLogger(name);
    }

    Logger(Class clazz) {
        logger = org.apache.log4j.Logger.getLogger(clazz);
    }

    @Override public void all(Object object) {
        logger.log(logger.getName(), LoggerLevel.ALL, object, null);
    }

    @Override public void all(Throwable throwable) {
        logger.log(logger.getName(), LoggerLevel.ALL, "", throwable);
    }

    @Override public void all(String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.ALL, StringUtil.mixParameters(msg, parameters), null);
    }

    @Override public void all(Throwable throwable, String msg) {
        logger.log(logger.getName(), LoggerLevel.ALL, msg, throwable);
    }

    @Override public void all(Throwable throwable, String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.ALL, StringUtil.mixParameters(msg, parameters), throwable);
    }

    @Override public void trace(Object object) {
        logger.log(logger.getName(), LoggerLevel.TRACE, object, null);
    }

    @Override public void trace(Throwable throwable) {
        logger.log(logger.getName(), LoggerLevel.TRACE, "", throwable);
    }

    @Override public void trace(String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.TRACE, StringUtil.mixParameters(msg, parameters), null);
    }

    @Override public void trace(Throwable throwable, String msg) {
        logger.log(logger.getName(), LoggerLevel.TRACE, msg, throwable);
    }

    @Override public void trace(Throwable throwable, String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.TRACE, StringUtil.mixParameters(msg, parameters), throwable);
    }

    @Override public void debug(Object object) {
        logger.log(logger.getName(), LoggerLevel.DEBUG, object, null);
    }

    @Override public void debug(Throwable throwable) {
        logger.log(logger.getName(), LoggerLevel.DEBUG, "", throwable);
    }

    @Override public void debug(String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.DEBUG, StringUtil.mixParameters(msg, parameters), null);
    }

    @Override public void debug(Throwable throwable, String msg) {
        logger.log(logger.getName(), LoggerLevel.DEBUG, msg, throwable);
    }

    @Override public void debug(Throwable throwable, String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.DEBUG, StringUtil.mixParameters(msg, parameters), throwable);
    }

    @Override public void info(Object object) {
        logger.log(logger.getName(), LoggerLevel.INFO, object, null);
    }

    @Override public void info(Throwable throwable) {
        logger.log(logger.getName(), LoggerLevel.INFO, "", throwable);
    }

    @Override public void info(String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.INFO, StringUtil.mixParameters(msg, parameters), null);
    }

    @Override public void info(Throwable throwable, String msg) {
        logger.log(logger.getName(), LoggerLevel.INFO, msg, throwable);
    }

    @Override public void info(Throwable throwable, String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.INFO, StringUtil.mixParameters(msg, parameters), throwable);
    }

    @Override public void warn(Object object) {
        logger.log(logger.getName(), LoggerLevel.WARN, object, null);
    }

    @Override public void warn(Throwable throwable) {
        logger.log(logger.getName(), LoggerLevel.WARN, "", throwable);
    }

    @Override public void warn(String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.WARN, StringUtil.mixParameters(msg, parameters), null);
    }

    @Override public void warn(Throwable throwable, String msg) {
        logger.log(logger.getName(), LoggerLevel.WARN, msg, throwable);
    }

    @Override public void warn(Throwable throwable, String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.WARN, StringUtil.mixParameters(msg, parameters), throwable);
    }

    @Override public void imp(Object object) {
        logger.log(logger.getName(), LoggerLevel.IMP, object, null);
    }

    @Override public void imp(Throwable throwable) {
        logger.log(logger.getName(), LoggerLevel.IMP, "", throwable);
    }

    @Override public void imp(String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.IMP, StringUtil.mixParameters(msg, parameters), null);
    }

    @Override public void imp(Throwable throwable, String msg) {
        logger.log(logger.getName(), LoggerLevel.IMP, msg, throwable);
    }

    @Override public void imp(Throwable throwable, String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.IMP, StringUtil.mixParameters(msg, parameters), throwable);
    }

    @Override public void error(Object object) {
        logger.log(logger.getName(), LoggerLevel.ERROR, object, null);
    }

    @Override public void error(Throwable throwable) {
        logger.log(logger.getName(), LoggerLevel.ERROR, "", throwable);
    }

    @Override public void error(String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.ERROR, StringUtil.mixParameters(msg, parameters), null);
    }

    @Override public void error(Throwable throwable, String msg) {
        logger.log(logger.getName(), LoggerLevel.ERROR, msg, throwable);
    }

    @Override public void error(Throwable throwable, String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.ERROR, StringUtil.mixParameters(msg, parameters), throwable);
    }

    @Override public void fatal(Object object) {
        logger.log(logger.getName(), LoggerLevel.FATAL, object, null);
    }

    @Override public void fatal(Throwable throwable) {
        logger.log(logger.getName(), LoggerLevel.FATAL, "", throwable);
    }

    @Override public void fatal(String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.FATAL, StringUtil.mixParameters(msg, parameters), null);
    }

    @Override public void fatal(Throwable throwable, String msg) {
        logger.log(logger.getName(), LoggerLevel.FATAL, msg, throwable);
    }

    @Override public void fatal(Throwable throwable, String msg, Object... parameters) {
        logger.log(logger.getName(), LoggerLevel.FATAL, StringUtil.mixParameters(msg, parameters), throwable);
    }

    @Override public void log(JingLoggerLevelItf level, Object object) {
        logger.log(logger.getName(), (Priority) level, object, null);
    }

    @Override public void log(JingLoggerLevelItf level, Throwable throwable) {
        logger.log(logger.getName(), (Priority) level, "", throwable);
    }

    @Override public void log(JingLoggerLevelItf level, Throwable throwable, String msg) {
        logger.log(logger.getName(), (Priority) level, msg, throwable);
    }

    @Override public void log(JingLoggerLevelItf level, Throwable throwable, String msg, Object... parameters) {
        logger.log(logger.getName(), (Priority) level, StringUtil.mixParameters(msg, parameters), throwable);
    }

    @Override public void log(JingLoggerLevelItf level, String msg, Object... parameters) {
        logger.log(logger.getName(), (Priority) level, StringUtil.mixParameters(msg, parameters), null);
    }
}
