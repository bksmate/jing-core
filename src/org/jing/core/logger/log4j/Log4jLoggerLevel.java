package org.jing.core.logger.log4j;

import org.apache.log4j.Level;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-02-03 <br>
 */
public class Log4jLoggerLevel extends Level {
    public static final Log4jLoggerLevel SQL = new Log4jLoggerLevel(29999, "SQL", 0);

    public static final Log4jLoggerLevel IMP = new Log4jLoggerLevel(39999, "IMP", 0);

    public static final HashSet<Level> EQUALS_PRIORITY = new HashSet<Level>();
    public static final HashSet<Level> GORE_PRIORITY = new HashSet<Level>();
    public static final HashSet<Level> IGNORE_PRIORITY = new HashSet<Level>();
    public static final HashMap<String, Level> LEVEL_MAPPING = new HashMap<String, Level>();

    protected Log4jLoggerLevel(int level, String levelStr, int syslogEquivalent) {
        super(level, levelStr, syslogEquivalent);
    }
}
