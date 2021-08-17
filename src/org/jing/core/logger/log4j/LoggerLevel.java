package org.jing.core.logger.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.jing.core.logger.itf.JingLoggerLevelItf;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-16 <br>
 */
public class LoggerLevel extends Level implements JingLoggerLevelItf {
    public static final Level IMP = new LoggerLevel(39999, "IMP", 3);

    protected LoggerLevel(int level, String levelStr, int syslogEquivalent) {
        super(level, levelStr, syslogEquivalent);
    }

    @Override public boolean isGreaterOrEquals(JingLoggerLevelItf e) {
        return super.isGreaterOrEqual((Priority) e);
    }
}
