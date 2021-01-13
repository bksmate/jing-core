package org.jing.core.logger.appender;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.lang.Pair3;
import org.jing.core.logger.JingLoggerEvent;
import org.jing.core.logger.SingleLogger;
import org.jing.core.logger.help.LoggerUtil;
import org.jing.core.util.GenericUtil;
import org.jing.core.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-01-13 <br>
 */
public class FileSizeAppender extends FileAppender {
    protected static long defaultSize = 10 * 1024 * 1024;

    protected long maxSize;

    protected long index = 0;

    public FileSizeAppender(Carrier paramC) throws JingException {
        super(paramC);
    }

    @Override protected void extendInit() throws JingException {
        String sizeString = paramC.getString("size", "10");
        try {
            float f = Float.parseFloat(sizeString);
            maxSize = (long) Math.floor(1024 * 1024 * f);
        }
        catch (Exception e) {
            SingleLogger.err("Use default size: {}", defaultSize);
            maxSize = defaultSize;
        }
    }

    @Override public void append(JingLoggerEvent event) {
        try {
            if (logFile.length() >= maxSize) {
                synchronized (writeLocker) {
                    writer.close();
                    writer = null;
                    renameToNewFile();
                    writeLocker.notifyAll();
                }
            }
            super.append(event);
        }
        catch (Exception ignored) {
        }
    }

    protected void renameToNewFile() {
        File logDir = logFile.getParentFile(), file;
        File[] files = logDir.listFiles();
        String logName = logFile.getName(), logName$;
        long maxIndex = -1, index$;
        int length = GenericUtil.countArray(files);
        Pair3<String, String, String> p = LoggerUtil.analysisFileName(logName);
        String regex = p.getA() + "\\.\\d+\\." + p.getB();
        for (int i$ = 0; i$ < length; i$++) {
            file = files[i$];
            logName$ = file.getName();
            if (!logName.equals(logName$) && logName$.matches(regex)) {
                index$ = StringUtil.parseInteger(LoggerUtil.analysisFileName(logName$).getC(), -1);
                if (index$ > maxIndex) {
                    maxIndex = index$;
                }
            }
        }
        maxIndex++;
        String backLogName = logDir.getAbsolutePath() + File.separator + p.getA() + "." + maxIndex + "." + p.getB();
        File backFile = new File(backLogName);
        if (!logFile.renameTo(backFile)) {
            SingleLogger.err("Failed to rename log file to {}", backLogName);
        }
        else {
            SingleLogger.log("Success to rename log file to {}", backLogName);
        }
        logFile = new File(absoluteFilePath);
        try {
            writer = new FileOutputStream(logFile, true);
        }
        catch (Exception e) {
            SingleLogger.err("Failed to create new log file: {}", e.getMessage());
        }
    }
}
