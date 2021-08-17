package org.jing.core.logger.local;

import org.jing.core.lang.BaseDto;
import org.jing.core.logger.itf.JingLoggerLevelItf;
import org.jing.core.logger.local.appender.BaseAppender;

import java.util.HashSet;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-31 <br>
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class LocalLoggerLevel extends BaseDto implements JingLoggerLevelItf {

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

    protected LocalLoggerLevel(int priority, String name, boolean synchronize) {
        this.name = name.toUpperCase();
        this.priority = priority;
        this.synchronize = synchronize;
    }

    protected LocalLoggerLevel(int priority, String name) {
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

    public boolean isGreaterOrEquals(LocalLoggerLevel level) {
        return this.priority == Integer.MAX_VALUE || this.priority >= level.priority;
    }

    @Override public boolean isGreaterOrEquals(JingLoggerLevelItf e) {
        return isGreaterOrEquals((LocalLoggerLevel) e);
    }

    public void loopAppend(LocalLoggerEvent event) {
        for (BaseAppender appender : levelConfig.appenderSet) {
            appender.append(event);
        }
    }

    public static final LocalLoggerLevel OFF = new LocalLoggerLevel(Integer.MAX_VALUE, "OFF", true);

    public static final LocalLoggerLevel FATAL = new LocalLoggerLevel(50000, "FATAL", true);

    public static final LocalLoggerLevel IMP = new LocalLoggerLevel(40000, "IMP", true);

    public static final LocalLoggerLevel ERROR = new LocalLoggerLevel(40000, "ERROR", true);

    public static final LocalLoggerLevel SQL = new LocalLoggerLevel(39999, "SQL", true);

    public static final LocalLoggerLevel WARN = new LocalLoggerLevel(30000, "WARN", true);

    public static final LocalLoggerLevel INFO = new LocalLoggerLevel(20000, "INFO", true);

    public static final LocalLoggerLevel DEBUG = new LocalLoggerLevel(10000, "DEBUG", true);

    public static final LocalLoggerLevel TRACE = new LocalLoggerLevel(5000, "TRACE", true);

    public static final LocalLoggerLevel ALL = new LocalLoggerLevel(Integer.MIN_VALUE, "ALL", true);
}
