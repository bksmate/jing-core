package org.jing.core.logger;

import org.jing.core.lang.Carrier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-31 <br>
 */
public class JingLoggerConfiguration {
    static Carrier configC;

    static ArrayList<JingLoggerLevel> levelList;

    static JingLoggerLevel rootLevel;

    static HashMap<String, ConcurrentLinkedQueue<byte[]>> contentMap;

    static HashMap<String, JingLoggerWriter> writerMap;

    static boolean stdOut = true;

    public synchronized void setGlobalStdOut(boolean stdOut) {
        JingLoggerConfiguration.stdOut = stdOut;
    }

    public synchronized boolean getGlobalStdOut() {
        return JingLoggerConfiguration.stdOut;
    }

    static String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

    public synchronized void setGlobalDateFormat(String dateFormat) {
        JingLoggerConfiguration.dateFormat = dateFormat;
    }

    public synchronized String getGlobalDateFormat() {
        return JingLoggerConfiguration.dateFormat;
    }

    static String encoding = "utf-8";

    public synchronized void setGlobalEncoding(String encoding) {
        JingLoggerConfiguration.encoding = encoding;
    }

    public synchronized String getGlobalEncoding() {
        return JingLoggerConfiguration.encoding;
    }

    static String format = "[%d][%t][%N->>-%M->>-%l][%p] - %m%n";

    public synchronized void setGlobalFormat(String format) {
        JingLoggerConfiguration.format = format;
    }

    public synchronized String getGlobalFormat() {
        return JingLoggerConfiguration.format;
    }

    static String newLine = "\r\n";

    public synchronized void setGlobalNewLine(String newLine) {
        JingLoggerConfiguration.newLine = newLine;
    }

    public synchronized String getGlobalNewLine() {
        return JingLoggerConfiguration.newLine;
    }
}
