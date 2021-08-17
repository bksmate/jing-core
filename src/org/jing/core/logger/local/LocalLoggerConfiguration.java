package org.jing.core.logger.local;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.logger.local.appender.EmptyAppender;
import org.jing.core.logger.local.appender.BaseAppender;

import java.util.ArrayList;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-31 <br>
 */
@SuppressWarnings("unused") public final class LocalLoggerConfiguration {
    private LocalLoggerConfiguration() {}

    static Carrier configC;

    static ArrayList<LocalLoggerLevel> levelList;

    static LocalLoggerLevel rootLevel = null;

    static boolean stdOut = true;

    static LocalLoggerLevel stdOutLevel = null;

    public static synchronized void setGlobalStdOut(boolean stdOut) {
        LocalLoggerConfiguration.stdOut = stdOut;
    }

    public static synchronized boolean getGlobalStdOut() {
        return LocalLoggerConfiguration.stdOut;
    }

    static String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

    public static synchronized void setGlobalDateFormat(String dateFormat) {
        LocalLoggerConfiguration.dateFormat = dateFormat;
    }

    public static synchronized String getGlobalDateFormat() {
        return LocalLoggerConfiguration.dateFormat;
    }

    static String encoding = "utf-8";

    public static synchronized void setGlobalEncoding(String encoding) {
        LocalLoggerConfiguration.encoding = encoding;
    }

    public static synchronized String getGlobalEncoding() {
        return LocalLoggerConfiguration.encoding;
    }

    static String format = "[%d][%t][%N->>-%M->>-%l][%p] - %m%n";

    public static synchronized void setGlobalFormat(String format) {
        LocalLoggerConfiguration.format = format;
    }

    public static synchronized String getGlobalFormat() {
        return LocalLoggerConfiguration.format;
    }

    static String newLine = "\r\n";

    public static synchronized void setGlobalNewLine(String newLine) {
        LocalLoggerConfiguration.newLine = newLine;
    }

    public static synchronized String getGlobalNewLine() {
        return LocalLoggerConfiguration.newLine;
    }

    private static BaseAppender emptyAppender = null;

    public static BaseAppender getEmptyAppender() throws JingException {
        synchronized (LocalLoggerConfiguration.class) {
            if (null == emptyAppender) {
                emptyAppender = new EmptyAppender(null);
            }
        }
        return emptyAppender;
    }
}
