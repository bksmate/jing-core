package org.jing.core.logger;

import org.jing.core.lang.Configuration;
import org.jing.core.util.DateUtil;
import org.jing.core.util.StringUtil;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

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
            output(JingLoggerLevel.ALL, StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
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
            output(JingLoggerLevel.TRACE, StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
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
            output(JingLoggerLevel.DEBUG, StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
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
            output(JingLoggerLevel.INFO, StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
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
            output(JingLoggerLevel.WARN, StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
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
            output(JingLoggerLevel.IMP, StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
        }
    }

    public void error(Object object) {
        if (JingLoggerLevel.ERROR.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.ERROR, StringUtil.parseString(object));
        }
    }

    public void error(Throwable throwable) {
        if (JingLoggerLevel.ERROR.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.ERROR, "\r\n" + StringUtil.getErrorStack(throwable));
        }
    }

    public void error(String msg, Object... parameters) {
        if (JingLoggerLevel.ERROR.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.ERROR, StringUtil.mixParameters(msg, parameters));
        }
    }

    public void error(String msg, Throwable throwable) {
        if (JingLoggerLevel.ERROR.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.ERROR, msg + "\r\n" + StringUtil.getErrorStack(throwable));
        }
    }

    public void error(String msg, Throwable throwable, Object... parameters) {
        if (JingLoggerLevel.ERROR.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {
            output(JingLoggerLevel.ERROR, StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
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
            output(JingLoggerLevel.FATAL, StringUtil.mixParameters(msg, parameters) + "\r\n" + StringUtil.getErrorStack(throwable));
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
        String content = generateMsg(level, msg);
        if (JingLoggerConfiguration.stdout) {
            System.out.print(content);
        }
        FileOutputStream writer;
        for (String outputPath : level.levelConfig.loggerPathSet) {
            try {
                JingLoggerConfiguration.contentMap.get(outputPath).offer(content.getBytes(level.levelConfig.encoding));
            }
            catch (Exception ignored) {}
        }
        write();
    }

    private void write() {
        byte[] content;
        FileOutputStream writer;
        for (Map.Entry<String, ConcurrentLinkedQueue<byte[]>> entry : JingLoggerConfiguration.contentMap.entrySet()) {
            try {
                writer = JingLoggerConfiguration.writerMap.get(entry.getKey());
                while (null != (content = entry.getValue().poll())) {
                    if (writer.getFD().valid()) {
                        writer.write(content);
                        writer.flush();
                    }
                }
            }
            catch (Exception ignored) {}
        }
    }

    private String generateMsg(JingLoggerLevel level, String msg) {
        String template = level.levelConfig.format;
        int length = template.length();
        char c;
        StackTraceElement trace = null;
        StringBuilder stbr = new StringBuilder();
        boolean flag = false;
        for (int i$ = 0; i$ < length; i$++) {
            c = template.charAt(i$);
            if (c == '%') {
                if (!flag) {
                    flag = true;
                }
                else {
                    stbr.append(c);
                }
            }
            else if (flag) {
                switch (c) {
                    // %d - timestamp
                    case 'd':
                        stbr.append(DateUtil.getCurrentDateString(level.levelConfig.dateFormat));
                        break;
                    // %t - threadName
                    case 't':
                        stbr.append(Thread.currentThread().getName());
                        break;
                    // %c - class
                    case 'c':
                        if (null == trace) {
                            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                            trace = stack[stack.length - 1];
                        }
                        stbr.append(null == trace ? "null" : trace.getClassName());
                        break;
                    // %M - method
                    case 'M':
                        if (null == trace) {
                            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                            if (stack.length > 4) {
                                trace = stack[4];
                            }
                            else {
                                trace = stack[stack.length - 1];
                            }
                        }
                        stbr.append(null == trace ? "null" : trace.getMethodName());
                        break;
                    // %l - line
                    case 'l':
                        if (null == trace) {
                            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                            trace = stack[stack.length - 1];
                        }
                        stbr.append(trace.getLineNumber());
                        break;
                    // %p - priority
                    case 'p':
                        stbr.append(level.name);
                        break;
                    // %m - message
                    case 'm':
                        stbr.append(msg);
                        break;
                    // %n - newline
                    case 'n':
                        stbr.append(JingLoggerConfiguration.newLine);
                        break;
                    // %N - name
                    case 'N':
                        stbr.append(name);
                        break;
                    default:
                        stbr.append('%').append(c);
                        break;
                }
                flag = false;
            }
            else {
                stbr.append(c);
            }
        }
        return stbr.toString();
    }

    public static void main(String[] args) {
        Configuration.getInstance();
        JingLogger logger = JingLogger.getLogger(JingLoggerLevel.class);
        logger.log(JingLoggerLevel.ALL, "test: {}", 123);
        logger.all("all: {}", 123);
        logger.debug("all: {}", 123);
        logger.trace("all: {}", 123);
        logger.info("all: {}", 123);
        logger.warn("all: {}", 123);
        logger.fatal("all: {}", 123);
        logger.imp("all: {}", 123);
        logger.error("error: {}", 123);

    }
}
