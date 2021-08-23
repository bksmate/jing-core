package org.jing.core.logger.local.appender;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.logger.local.LocalLoggerEvent;
import org.jing.core.logger.sys.SingleLogger;
import org.jing.core.util.DateUtil;
import org.jing.core.util.GenericUtil;
import org.jing.core.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-01-13 <br>
 */
@SuppressWarnings({ "Duplicates", "WeakerAccess" }) public class FileDailyAppender extends FileAppender {
    protected String dateFormat;

    protected Date logFileDate;

    protected long timeSize;

    public FileDailyAppender(Carrier paramC) throws JingException {
        super(paramC);
    }

    @Override protected void extendInit() throws JingException {
        this.dateFormat = paramC.getStringByName("date-format", "yyyy-MM-dd");
        this.logFileDate = DateUtil.getDate(DateUtil.getDateString(new Date(logFile.lastModified()), "yyyyMMdd"), "yyyyMMdd");
        this.timeSize = StringUtil.parseInteger(paramC.getStringByName("times", ""), 1) * 24 * 60 * 60 * 1000;
    }

    @Override public void append(LocalLoggerEvent event) {
        try {
            Date newDate = new Date();
            synchronized (writeLocker) {
                if (Math.abs(newDate.getTime() - logFileDate.getTime()) >= timeSize) {
                    writer.close();
                    writer = null;
                    renameToNewFile(newDate);
                    writeLocker.notifyAll();
                }
            }
            super.append(event);
        }
        catch (Exception ignored) {
        }
    }

    protected void renameToNewFile(Date newDate) {
        File logDir = logFile.getParentFile(), file;
        File[] files = logDir.listFiles();
        String logName = logFile.getName(), newLogName;
        long maxIndex = -1, index$;
        int length = GenericUtil.countArray(files), dotIndex;
        dotIndex = logName.lastIndexOf(".");
        if (-1 == dotIndex) {
            newLogName = logDir.getAbsolutePath() + File.separator + logName + "." + DateUtil.getDateString(logFileDate, dateFormat);
        }
        else {
            newLogName = logDir.getAbsolutePath() + File.separator + logName.substring(0, dotIndex + 1) + DateUtil.getDateString(logFileDate, dateFormat) + logName.substring(dotIndex);
        }
        File backFile = new File(newLogName);
        if (backFile.exists() && backFile.isFile()) {
            SingleLogger.log("Try to delete log file existed: {}", newLogName);
            if (!backFile.delete()) {
                SingleLogger.err("Failed to delete log file to {}", newLogName);
            }
        }
        if (!logFile.renameTo(backFile)) {
            SingleLogger.err("Failed to rename log file to {}", newLogName);
        }
        else {
            SingleLogger.log("Success to rename log file to {}", newLogName);
        }
        logFile = new File(absoluteFilePath);
        try {
            writer = new FileOutputStream(logFile, true);
            logFileDate = DateUtil.getDate(DateUtil.getDateString(newDate, "yyyyMMdd"), "yyyyMMdd");
        }
        catch (Exception e) {
            SingleLogger.err("Failed to create new log file: {}", e.getMessage());
        }
    }
}
