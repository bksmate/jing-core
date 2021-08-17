package org.jing.core.logger.local.help;

import org.jing.core.logger.local.appender.BaseAppender;
import org.jing.core.util.StringUtil;

import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-01-12 <br>
 */
public class ResourcePool {
    private static volatile ResourcePool ourInstance = null;

    public static ResourcePool getInstance() {
        if (null == ourInstance) {
            synchronized (ResourcePool.class) {
                if (null == ourInstance) {
                    ourInstance = new ResourcePool();
                }
            }
        }
        return ourInstance;
    }

    private ResourcePool() {
        outputStreamMap = new HashMap<>();
        appenderMap = new HashMap<>();
    }

    private final HashMap<String, FileOutputStream> outputStreamMap;

    private final HashMap<String, BaseAppender> appenderMap;

    public FileOutputStream getFileOutputStream(String absoluteFilePath) {
        synchronized (ResourcePool.class) {
            return outputStreamMap.get(absoluteFilePath);
        }
    }

    public void addFileOutputStream(String absoluteFilePath, FileOutputStream outputStream) {
        synchronized (ResourcePool.class) {
            outputStreamMap.put(absoluteFilePath, outputStream);
        }
    }

    public BaseAppender getAppenderByName(String name) {
        synchronized (ResourcePool.class) {
            return appenderMap.get(StringUtil.ifEmpty(name).toUpperCase());
        }
    }

    public void addAppender(String name, BaseAppender appender) {
        synchronized (ResourcePool.class) {
            appenderMap.put(StringUtil.ifEmpty(name).toUpperCase(), appender);
        }
    }
}
