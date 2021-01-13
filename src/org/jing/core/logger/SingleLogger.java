package org.jing.core.logger;

import org.jing.core.util.DateUtil;
import org.jing.core.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Description: 非线程安全logger, 依赖环境变量进行输出路径配置. <br>
 *
 * @author: bks <br>
 * @createDate: 2021-01-13 <br>
 */
public class SingleLogger {
    private static volatile FileOutputStream writer = null;

    private static final String ENCODING = "utf-8";

    static {
        try {
            String logDirPath = System.getProperty("JING_LOG_DIR");
            File logDir = new File(logDirPath);
            if (logDir.exists() || logDir.mkdirs()) {
                File logFile = new File(logDirPath + File.separator + "start.log");
                writer = new FileOutputStream(logFile, true);
            }
        }
        catch (Exception ignored) {}
    }

    private SingleLogger() {}

    public static synchronized void err(String msg, Object... params) {
        msg = StringUtil.mixParameters(msg, params);
        write("Error", msg);
    }

    public static synchronized void log(String msg, Object... params) {
        msg = StringUtil.mixParameters(msg, params);
        write("System", msg);
    }

    private static void write(String logName, String content) {
        content = String.format("[%s][%s]: %s%s",
            DateUtil.getCurrentDateString("yyyy-MM-dd HH:mm:ss.SSS"),
            logName,
            content,
            "\r\n");
        try {
            if (null != writer && writer.getFD().valid()) {
                writer.write(content.getBytes(ENCODING));
                writer.flush();
            }
        }
        catch (Exception ignored) {}
        System.out.print(content);
    }
}
