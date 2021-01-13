package org.jing.core.logger;

import org.jing.core.lang.BaseDto;
import org.jing.core.logger.appender.BaseAppender;

import java.util.HashSet;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-31 <br>
 */
@SuppressWarnings({ "WeakerAccess", "unused" }) public class JingLoggerLevel extends BaseDto {
    public static final class LevelConfig extends BaseDto {
        String format;

        String encoding;

        String dateFormat;

        HashSet<BaseAppender> appenderSet;

        LevelConfig() {
            appenderSet = new HashSet<>();
        }

        public synchronized void setFormat(String format) {
            this.format = format;
        }

        public synchronized String getFormat() {
            return this.format;
        }

        public synchronized void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public synchronized String getEncoding() {
            return this.encoding;
        }

        public synchronized void setDateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
        }

        public synchronized String getDateFormat() {
            return this.dateFormat;
        }

        public synchronized void addAppender(BaseAppender appender) {
            appenderSet.add(appender);
        }
    }

    protected String name;

    protected int priority;

    protected boolean synchronize;

    protected LevelConfig levelConfig;

    protected JingLoggerLevel(int priority, String name, boolean synchronize) {
        this.name = name.toUpperCase();
        this.priority = priority;
        this.synchronize = synchronize;
    }

    protected JingLoggerLevel(int priority, String name) {
        this.name = name.toUpperCase();
        this.priority = priority;
        this.synchronize = true;
    }

    public LevelConfig getLevelConfig() {
        return levelConfig;
    }

    void setLevelConfig(LevelConfig levelConfig) {
        this.levelConfig = levelConfig;
    }

    public boolean isGreaterOrEquals(JingLoggerLevel level) {
        return this.priority == Integer.MAX_VALUE || this.priority >= level.priority;
    }

    public void loopAppend(JingLoggerEvent event) {
        for (BaseAppender appender : levelConfig.appenderSet) {
            appender.append(event);
        }
    }

    public static final JingLoggerLevel OFF = new JingLoggerLevel(Integer.MAX_VALUE, "OFF", true);

    public static final JingLoggerLevel FATAL = new JingLoggerLevel(50000, "FATAL", true);

    public static final JingLoggerLevel IMP = new JingLoggerLevel(40000, "IMP", true);

    public static final JingLoggerLevel ERROR = new JingLoggerLevel(40000, "ERROR", true);

    public static final JingLoggerLevel SQL = new JingLoggerLevel(39999, "SQL", true);

    public static final JingLoggerLevel WARN = new JingLoggerLevel(30000, "WARN", true);

    public static final JingLoggerLevel INFO = new JingLoggerLevel(20000, "INFO", true);

    public static final JingLoggerLevel DEBUG = new JingLoggerLevel(10000, "DEBUG", true);

    public static final JingLoggerLevel TRACE = new JingLoggerLevel(5000, "TRACE", true);

    public static final JingLoggerLevel ALL = new JingLoggerLevel(Integer.MIN_VALUE, "ALL", true);
}
