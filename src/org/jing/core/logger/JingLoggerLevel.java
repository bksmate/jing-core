package org.jing.core.logger;

import org.jing.core.lang.BaseDto;

import java.util.HashSet;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-31 <br>
 */
public class JingLoggerLevel extends BaseDto {
    static final class LevelConfig extends BaseDto{
        HashSet<String> loggerPathSet;

        String format;

        String encoding;

        LevelConfig() {
            loggerPathSet = new HashSet<>();
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

    JingLoggerLevel setLevelConfig(LevelConfig levelConfig) {
        this.levelConfig = levelConfig;
        return this;
    }

    public boolean isGreaterOrEquals(JingLoggerLevel level) {
        return this.priority == Integer.MAX_VALUE || this.priority >= level.priority;
    }

    public static final JingLoggerLevel OFF = new JingLoggerLevel(Integer.MAX_VALUE, "OFF", true);

    public static final JingLoggerLevel FATAL = new JingLoggerLevel(50000, "FATAL", true);

    public static final JingLoggerLevel ERROR = new JingLoggerLevel(40000, "ERROR", true);

    public static final JingLoggerLevel WARN = new JingLoggerLevel(30000, "WARN", true);

    public static final JingLoggerLevel INFO = new JingLoggerLevel(20000, "INFO", true);

    public static final JingLoggerLevel DEBUG = new JingLoggerLevel(10000, "DEBUG", true);

    public static final JingLoggerLevel TRACE = new JingLoggerLevel(5000, "TRACE", true);

    public static final JingLoggerLevel ALL = new JingLoggerLevel(Integer.MIN_VALUE, "ALL", true);
}
