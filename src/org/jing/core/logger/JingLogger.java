package org.jing.core.logger;

import org.jing.core.lang.Configuration;
import org.jing.core.util.StringUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-31 <br>
 */
@SuppressWarnings({ "unused", "WeakerAccess" }) public class JingLogger {

    private String name;

    private JingLogger() {}

    public static JingLogger getLogger(Class clazz) {
        Configuration.getInstance();
        JingLogger logger = new JingLogger();
        logger.name = clazz.getName();
        return logger;
    }

    public static JingLogger getLogger(String name) {
        JingLogger logger = new JingLogger();
        logger.name = name;
        return logger;
    }

    public void all(Object object) {
        if (JingLoggerLevel.ALL.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.ALL, StringUtil.parseString(object));
        }
    }

    public void all(String msg, Object... parameters) {
        if (JingLoggerLevel.ALL.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.ALL, StringUtil.mixParameters(msg, parameters));
        }
    }

    public void all(String msg, Throwable throwable, Object... parameters) {
        if (JingLoggerLevel.ALL.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.ALL, StringUtil.mixParameters(msg, parameters) + JingLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    public void trace(Object object) {
        if (JingLoggerLevel.TRACE.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.TRACE, StringUtil.parseString(object));
        }
    }

    public void trace(String msg, Object... parameters) {
        if (JingLoggerLevel.TRACE.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.TRACE, StringUtil.mixParameters(msg, parameters));
        }
    }

    public void trace(String msg, Throwable throwable, Object... parameters) {
        if (JingLoggerLevel.TRACE.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.TRACE, StringUtil.mixParameters(msg, parameters) + JingLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    public void debug(Object object) {
        if (JingLoggerLevel.DEBUG.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.DEBUG, StringUtil.parseString(object));
        }
    }

    public void debug(String msg, Object... parameters) {
        if (JingLoggerLevel.DEBUG.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.DEBUG, StringUtil.mixParameters(msg, parameters));
        }
    }

    public void debug(String msg, Throwable throwable, Object... parameters) {
        if (JingLoggerLevel.DEBUG.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.DEBUG, StringUtil.mixParameters(msg, parameters) + JingLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    public void info(Object object) {
        if (JingLoggerLevel.INFO.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.INFO, StringUtil.parseString(object));
        }
    }

    public void info(String msg, Object... parameters) {
        if (JingLoggerLevel.INFO.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.INFO, StringUtil.mixParameters(msg, parameters));
        }
    }

    public void info(String msg, Throwable throwable, Object... parameters) {
        if (JingLoggerLevel.INFO.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.INFO, StringUtil.mixParameters(msg, parameters) + JingLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    public void warn(Object object) {
        if (JingLoggerLevel.WARN.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.WARN, StringUtil.parseString(object));
        }
    }

    public void warn(String msg, Object... parameters) {
        if (JingLoggerLevel.WARN.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.WARN, StringUtil.mixParameters(msg, parameters));
        }
    }

    public void warn(String msg, Throwable throwable, Object... parameters) {
        if (JingLoggerLevel.WARN.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.WARN, StringUtil.mixParameters(msg, parameters) + JingLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    public void imp(Object object) {
        if (JingLoggerLevel.IMP.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.IMP, StringUtil.parseString(object));
        }
    }

    public void imp(String msg, Object... parameters) {
        if (JingLoggerLevel.IMP.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.IMP, StringUtil.mixParameters(msg, parameters));
        }
    }

    public void imp(String msg, Throwable throwable, Object... parameters) {
        if (JingLoggerLevel.IMP.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.IMP, StringUtil.mixParameters(msg, parameters) + JingLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    public void sql(int session, String msg) {
        if (JingLoggerLevel.SQL.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.SQL, String.format("[Session: %s]%s%s", session, JingLoggerConfiguration.newLine, msg));
        }
    }

    public void sqlWithHash(Object hashObject, String msg) {
        if (JingLoggerLevel.SQL.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.SQL, String.format("[Session: %s]%s%s", hashObject.hashCode(), JingLoggerConfiguration.newLine, msg));
        }
    }

    public void sql(int session, String msg, String parameters) {
        if (JingLoggerLevel.SQL.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.SQL, String.format("[Session: %s]%s%s [%s]", session, JingLoggerConfiguration.newLine, msg, parameters));
        }
    }

    public void sqlWithHash(Object hashObject, String msg, String parameters) {
        if (JingLoggerLevel.SQL.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.SQL, String.format("[Session: %s]%s%s [%s]", hashObject.hashCode(), JingLoggerConfiguration.newLine, msg, parameters));
        }
    }

    public void sql(int session, String msg, Object... parameters) {
        if (JingLoggerLevel.SQL.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.SQL, StringUtil.mixParameters(String.format("[Session: %s]%s%s", session, JingLoggerConfiguration.newLine, msg), parameters));
        }
    }

    public void sqlWithHash(Object hashObject, String msg, Object... parameters) {
        if (JingLoggerLevel.SQL.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.SQL, StringUtil.mixParameters(String.format("[Session: %s]%s%s", hashObject.hashCode(), JingLoggerConfiguration.newLine, msg), parameters));
        }
    }

    public void error(Object object) {
        if (JingLoggerLevel.ERROR.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.ERROR, StringUtil.parseString(object));
        }
    }

    public void error(Throwable throwable) {
        if (JingLoggerLevel.ERROR.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.ERROR, JingLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    public void error(String msg, Object... parameters) {
        if (JingLoggerLevel.ERROR.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.ERROR, StringUtil.mixParameters(msg, parameters));
        }
    }

    public void error(String msg, Throwable throwable) {
        if (JingLoggerLevel.ERROR.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.ERROR, msg + JingLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    public void error(String msg, Throwable throwable, Object... parameters) {
        if (JingLoggerLevel.ERROR.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.ERROR, StringUtil.mixParameters(msg, parameters) + JingLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    public void fatal(Object object) {
        if (JingLoggerLevel.FATAL.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.FATAL, StringUtil.parseString(object));
        }
    }

    public void fatal(String msg, Object... parameters) {
        if (JingLoggerLevel.FATAL.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.FATAL, StringUtil.mixParameters(msg, parameters));
        }
    }

    public void fatal(String msg, Throwable throwable, Object... parameters) {
        if (JingLoggerLevel.FATAL.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.FATAL, StringUtil.mixParameters(msg, parameters) + JingLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    public void log(JingLoggerLevel level, Object object) {
        if (level.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(level, StringUtil.parseString(object));
        }
    }

    public void log(JingLoggerLevel level, Throwable throwable) {
        if (level.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(level, JingLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    public void log(JingLoggerLevel level, Throwable throwable, String msg) {
        if (level.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(level, msg+ JingLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    public void log(JingLoggerLevel level, Throwable throwable, String msg, Object... parameters) {
        if (level.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(level, StringUtil.mixParameters(msg, parameters) + JingLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }
    
    public void log(JingLoggerLevel level, String msg, Object... parameters) {
        if (level.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(level, StringUtil.mixParameters(msg, parameters));
        }
    }

    private void output(JingLoggerLevel level, String msg) {
        if (null == level.levelConfig) {
            return;
        }
        JingLoggerEvent event = new JingLoggerEvent();
        event
            .setLoggerName(this.name)
            .setLevel(level)
            .setContent(msg)
            .generateContent();

        level.loopAppend(event);
    }

    public static void main(String[] args) {
        // Configuration.getInstance();
        JingLogger logger = JingLogger.getLogger(JingLoggerLevel.class);
        logger.log(JingLoggerLevel.ALL, "test: {}", 123);
        logger.all("all: {}", 123);
        logger.debug("debug: {}", 123);
        logger.trace("trace: {}", 123);
        logger.info("info: {}", 123);
        logger.warn("warn: {}", 123);
        logger.fatal("fatal: {}", 123);
        logger.imp("imp: {}", 123);
        logger.sql(112233, "SELECT * FROM SA", 123123);
        logger.error("error: {}", 123);
        // System.out.println(CommonDemo.getJavaStackTrace());

    }
}
