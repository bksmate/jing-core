package org.jing.core.logger.local.appender;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.logger.local.LocalLoggerEvent;
import org.jing.core.logger.sys.SingleLogger;
import org.jing.core.logger.local.dispatcher.BaseDispatcher;
import org.jing.core.logger.local.help.ResourcePool;
import org.jing.core.util.FileUtil;
import org.jing.core.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-01-12 <br>
 */
@SuppressWarnings("WeakerAccess") public class FileAppender extends BaseAppender {
    protected FileOutputStream writer;

    protected String absoluteFilePath;

    protected File logFile;

    public FileAppender(Carrier paramC) throws JingException {
        super(paramC);
    }

    @Override protected void init() throws JingException {
        if (null == paramC) {
            throw new JingException("empty parameter carrier");
        }
        String filePath = paramC.getStringByName("file", "");
        if (StringUtil.isEmpty(filePath)) {
            throw new JingException("empty file path");
        }
        logFile = new File(FileUtil.buildPathWithHome(filePath));
        filePath = logFile.getAbsolutePath();
        FileOutputStream writer = ResourcePool.getInstance().getFileOutputStream(filePath);
        if (null != writer) {
            throw new JingException("fileOutputStream has already exists: " + filePath);
        }
        try {
            File parentDir = logFile.getParentFile();
            if (null != parentDir && !parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    throw new JingException("failed to create log directory");
                }
            }
            writer = new FileOutputStream(filePath, true);
            ResourcePool.getInstance().addFileOutputStream(filePath, writer);
            this.writer = writer;
            this.absoluteFilePath = filePath;
        }
        catch (Exception e) {
            throw new JingException(e, e.getMessage());
        }
        extendInit();
    }

    protected void extendInit() throws JingException {}

    @Override protected void createDispatcher() {
        dispatcher = new Dispatcher(this);
        dispatcher.setName("LocalLogger-Dispatcher-" + dispatcher.getName());
        dispatcher.setDaemon(true);
        dispatcher.start();
    }

    @Override public void write(LocalLoggerEvent event) {
        try {
            synchronized (writeLocker) {
                if (null == writer) {
                    writeLocker.wait();
                }
                writer.write(event.getContent().getBytes(event.getLevel().getLevelConfig().getEncoding()));
            }
        }
        catch (Exception ignored) {}
    }

    @Override public void flush(LocalLoggerEvent[] events) {
        try {
            writer.flush();
        }
        catch (Exception ignored) {}
    }

    @Override public void close() {
        super.close();
        try {
            writer.close();
        }
        catch (Exception e) {
            SingleLogger.err("Failed to close FileOutputStream");
        }
    }

    protected static class Dispatcher extends BaseDispatcher {
        protected Dispatcher(BaseAppender appender) {
            super(appender);
        }
    }
}
