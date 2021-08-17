package org.jing.core.logger.local;

import org.jing.core.lang.Configuration;
import org.jing.core.logger.JingLogger;
import org.jing.core.logger.itf.JingLoggerLevelItf;
import org.jing.core.util.StringUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-31 <br>
 */
@SuppressWarnings({ "unused", "WeakerAccess" })
public class LocalLogger extends JingLogger {

    private String name;

    private LocalLogger() {}

    public static LocalLogger getLogger(Class clazz) {
        Configuration.getInstance();
        LocalLogger logger = new LocalLogger();
        logger.name = clazz.getName();
        return logger;
    }

    public static LocalLogger getLogger(String name) {
        LocalLogger logger = new LocalLogger();
        logger.name = name;
        return logger;
    }

    @Override public void all(Object object) {
        if (LocalLoggerLevel.ALL.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.ALL, StringUtil.parseString(object));
        }
    }

    @Override public void all(Throwable throwable) {
        if (LocalLoggerLevel.ALL.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.ALL, StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void all(String msg, Object... parameters) {
        if (LocalLoggerLevel.ALL.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.ALL, StringUtil.mixParameters(msg, parameters));
        }
    }

    @Override public void all(Throwable throwable, String msg) {
        if (LocalLoggerLevel.ALL.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.ALL, msg + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void all(Throwable throwable, String msg, Object... parameters) {
        if (LocalLoggerLevel.ALL.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.ALL, StringUtil.mixParameters(msg, parameters) + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void trace(Object object) {
        if (LocalLoggerLevel.TRACE.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.TRACE, StringUtil.parseString(object));
        }
    }

    @Override public void trace(Throwable throwable) {
        if (LocalLoggerLevel.TRACE.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.TRACE, StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void trace(String msg, Object... parameters) {
        if (LocalLoggerLevel.TRACE.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.TRACE, StringUtil.mixParameters(msg, parameters));
        }
    }

    @Override public void trace(Throwable throwable, String msg) {
        if (LocalLoggerLevel.TRACE.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.TRACE, msg + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void trace(Throwable throwable, String msg, Object... parameters) {
        if (LocalLoggerLevel.TRACE.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.TRACE, StringUtil.mixParameters(msg, parameters) + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void debug(Object object) {
        if (LocalLoggerLevel.DEBUG.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.DEBUG, StringUtil.parseString(object));
        }
    }

    @Override public void debug(Throwable throwable) {
        if (LocalLoggerLevel.DEBUG.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.DEBUG, StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void debug(String msg, Object... parameters) {
        if (LocalLoggerLevel.DEBUG.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.DEBUG, StringUtil.mixParameters(msg, parameters));
        }
    }

    @Override public void debug(Throwable throwable, String msg) {
        if (LocalLoggerLevel.DEBUG.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.DEBUG, msg + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void debug(Throwable throwable, String msg, Object... parameters) {
        if (LocalLoggerLevel.DEBUG.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.DEBUG, StringUtil.mixParameters(msg, parameters) + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void info(Object object) {
        if (LocalLoggerLevel.INFO.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.INFO, StringUtil.parseString(object));
        }
    }

    @Override public void info(Throwable throwable) {
        if (LocalLoggerLevel.INFO.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.INFO, StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void info(String msg, Object... parameters) {
        if (LocalLoggerLevel.INFO.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.INFO, StringUtil.mixParameters(msg, parameters));
        }
    }

    @Override public void info(Throwable throwable, String msg) {
        if (LocalLoggerLevel.INFO.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.INFO, msg + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void info(Throwable throwable, String msg, Object... parameters) {
        if (LocalLoggerLevel.INFO.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.INFO, StringUtil.mixParameters(msg, parameters) + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void warn(Object object) {
        if (LocalLoggerLevel.WARN.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.WARN, StringUtil.parseString(object));
        }
    }

    @Override public void warn(Throwable throwable) {
        if (LocalLoggerLevel.WARN.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.WARN, StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void warn(String msg, Object... parameters) {
        if (LocalLoggerLevel.WARN.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.WARN, StringUtil.mixParameters(msg, parameters));
        }
    }

    @Override public void warn(Throwable throwable, String msg) {
        if (LocalLoggerLevel.WARN.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.WARN, msg + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void warn(Throwable throwable, String msg, Object... parameters) {
        if (LocalLoggerLevel.WARN.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.WARN, StringUtil.mixParameters(msg, parameters) + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void imp(Object object) {
        if (LocalLoggerLevel.IMP.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.IMP, StringUtil.parseString(object));
        }
    }

    @Override public void imp(Throwable throwable) {
        if (LocalLoggerLevel.IMP.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.IMP, StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void imp(String msg, Object... parameters) {
        if (LocalLoggerLevel.IMP.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.IMP, StringUtil.mixParameters(msg, parameters));
        }
    }

    @Override public void imp(Throwable throwable, String msg) {
        if (LocalLoggerLevel.IMP.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.IMP, msg + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void imp(Throwable throwable, String msg, Object... parameters) {
        if (LocalLoggerLevel.IMP.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.IMP, StringUtil.mixParameters(msg, parameters) + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void error(Object object) {
        if (LocalLoggerLevel.ERROR.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.ERROR, StringUtil.parseString(object));
        }
    }

    @Override public void error(Throwable throwable) {
        if (LocalLoggerLevel.ERROR.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.ERROR, LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void error(String msg, Object... parameters) {
        if (LocalLoggerLevel.ERROR.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.ERROR, StringUtil.mixParameters(msg, parameters));
        }
    }

    @Override public void error(Throwable throwable, String msg) {
        if (LocalLoggerLevel.ERROR.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.ERROR, msg + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void error(Throwable throwable, String msg, Object... parameters) {
        if (LocalLoggerLevel.ERROR.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.ERROR, StringUtil.mixParameters(msg, parameters) + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void fatal(Object object) {
        if (LocalLoggerLevel.FATAL.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.FATAL, StringUtil.parseString(object));
        }
    }

    @Override public void fatal(Throwable throwable) {
        if (LocalLoggerLevel.FATAL.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.FATAL, StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void fatal(String msg, Object... parameters) {
        if (LocalLoggerLevel.FATAL.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.FATAL, StringUtil.mixParameters(msg, parameters));
        }
    }

    @Override public void fatal(Throwable throwable, String msg) {
        if (LocalLoggerLevel.FATAL.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.FATAL, msg + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void fatal(Throwable throwable, String msg, Object... parameters) {
        if (LocalLoggerLevel.FATAL.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output(LocalLoggerLevel.FATAL, StringUtil.mixParameters(msg, parameters) + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void log(JingLoggerLevelItf level, Object object) {
        if (level.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output((LocalLoggerLevel) level, StringUtil.parseString(object));
        }
    }

    @Override public void log(JingLoggerLevelItf level, Throwable throwable) {
        if (level.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output((LocalLoggerLevel) level, LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void log(JingLoggerLevelItf level, Throwable throwable, String msg) {
        if (level.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output((LocalLoggerLevel) level, msg+ LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void log(JingLoggerLevelItf level, Throwable throwable, String msg, Object... parameters) {
        if (level.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output((LocalLoggerLevel) level, StringUtil.mixParameters(msg, parameters) + LocalLoggerConfiguration.newLine + StringUtil.getErrorStack(throwable));
        }
    }

    @Override public void log(JingLoggerLevelItf level, String msg, Object... parameters) {
        if (level.isGreaterOrEquals(LocalLoggerConfiguration.rootLevel)) {
            output((LocalLoggerLevel) level, StringUtil.mixParameters(msg, parameters));
        }
    }

    private void output(LocalLoggerLevel level, String msg) {
        if (null == level.levelConfig) {
            return;
        }
        LocalLoggerEvent event = new LocalLoggerEvent();
        event
            .setLoggerName(this.name)
            .setLevel(level)
            .setContent(msg)
            .generateContent();

        level.loopAppend(event);
    }
}
