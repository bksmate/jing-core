package org.jing.core.logger.appender;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.logger.JingLoggerEvent;
import org.jing.core.logger.dispatcher.BaseDispatcher;
import org.jing.core.logger.help.ResourcePool;
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
public class FileAppender extends BaseAppender {
    protected FileOutputStream writer;

    protected String absoluteFilePath;

    public FileAppender(Carrier paramC) throws JingException {
        super(paramC);
    }

    @Override protected void init() throws JingException {
        if (null == paramC) {
            throw new JingException("Empty parameter carrier");
        }
        String filePath = paramC.getString("file", "");
        if (StringUtil.isEmpty(filePath)) {
            throw new JingException("Empty file path");
        }
        File logFile = new File(FileUtil.buildPathWithHome(filePath));
        filePath = logFile.getAbsolutePath();
        FileOutputStream writer = ResourcePool.getInstance().getFileOutputStream(filePath);
        if (null != writer) {
            throw new JingException("FileOutputStream has already exists: " + filePath);
        }
        try {
            File parentDir = logFile.getParentFile();
            if (null != parentDir && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            writer = new FileOutputStream(filePath, true);
            ResourcePool.getInstance().addFileOutputStream(filePath, writer);
            this.writer = writer;
            this.absoluteFilePath = filePath;
        }
        catch (Exception e) {
            throw new JingException(e);
        }
    }

    @Override protected void createDispatcher() {
        dispatcher = new Dispatcher(this);
        dispatcher.setName("JingLogger-Dispatcher-" + dispatcher.getName());
        dispatcher.setDaemon(true);
        dispatcher.start();
    }

    @Override public void write(JingLoggerEvent event) {
        try {
            writer.write(event.getContent().getBytes(event.getLevel().getLevelConfig().getEncoding()));
        }
        catch (Exception ignored) {}
    }

    @Override public void flush(JingLoggerEvent[] events) {
        try {
            writer.flush();
        }
        catch (Exception ignored) {}
    }

    protected static class Dispatcher extends BaseDispatcher {

        protected Dispatcher(BaseAppender appender) {
            super(appender);
        }
    }
}
