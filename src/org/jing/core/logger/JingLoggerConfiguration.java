package org.jing.core.logger;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.logger.appender.EmptyAppender;
import org.jing.core.logger.appender.BaseAppender;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-31 <br>
 */
@SuppressWarnings("unused") public final class JingLoggerConfiguration {
    private JingLoggerConfiguration() {}

    static Carrier configC;

    static ArrayList<JingLoggerLevel> levelList;

    static JingLoggerLevel rootLevel;

    static HashMap<String, JingLoggerWriter> writerMap;

    static boolean stdOut = true;

    public static synchronized void setGlobalStdOut(boolean stdOut) {
        JingLoggerConfiguration.stdOut = stdOut;
    }

    public static synchronized boolean getGlobalStdOut() {
        return JingLoggerConfiguration.stdOut;
    }

    static String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

    public static synchronized void setGlobalDateFormat(String dateFormat) {
        JingLoggerConfiguration.dateFormat = dateFormat;
    }

    public static synchronized String getGlobalDateFormat() {
        return JingLoggerConfiguration.dateFormat;
    }

    static String encoding = "utf-8";

    public static synchronized void setGlobalEncoding(String encoding) {
        JingLoggerConfiguration.encoding = encoding;
    }

    public static synchronized String getGlobalEncoding() {
        return JingLoggerConfiguration.encoding;
    }

    static String format = "[%d][%t][%N->>-%M->>-%l][%p] - %m%n";

    public static synchronized void setGlobalFormat(String format) {
        JingLoggerConfiguration.format = format;
    }

    public static synchronized String getGlobalFormat() {
        return JingLoggerConfiguration.format;
    }

    static String newLine = "\r\n";

    public static synchronized void setGlobalNewLine(String newLine) {
        JingLoggerConfiguration.newLine = newLine;
    }

    public static synchronized String getGlobalNewLine() {
        return JingLoggerConfiguration.newLine;
    }

    private static BaseAppender emptyAppender = null;

    public static BaseAppender getEmptyAppender() throws JingException {
        synchronized (JingLoggerConfiguration.class) {
            if (null == emptyAppender) {
                emptyAppender = new EmptyAppender(null);
            }
        }
        return emptyAppender;
    }
}
