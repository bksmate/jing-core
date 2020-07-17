package org.jing.core.logger.log4j.impl;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.jing.core.logger.log4j.Log4jLoggerLevel;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-02-13 <br>
 */
public class Log4jDailyRollingFileAppender extends DailyRollingFileAppender {
    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        // 优先判断入参Priority是不是和等于关系里的匹配.
        boolean flag = !Log4jLoggerLevel.EQUALS_PRIORITY.isEmpty()
            && this.threshold.equals(priority) && Log4jLoggerLevel.EQUALS_PRIORITY.contains(this.threshold);
        // 如果都不匹配, 则再进行大于等于判断.
        // 如果当前Appender的Priority或者入参Priority存在于忽略集合里, 则不会输出日志.
        if (!flag && Log4jLoggerLevel.EQUALS_PRIORITY.isEmpty()) {
            for (Level level$ : Log4jLoggerLevel.GORE_PRIORITY) {
                flag = !Log4jLoggerLevel.IGNORE_PRIORITY.contains(this.threshold)
                    && !Log4jLoggerLevel.IGNORE_PRIORITY.contains(priority)
                    && this.threshold.isGreaterOrEqual(level$);
                if (flag) {
                    break;
                }
            }
        }
        // 如果equals规则为空或者gore规则为空, 则按照原来的规则进行匹配输出
        if (!flag && Log4jLoggerLevel.EQUALS_PRIORITY.isEmpty() && Log4jLoggerLevel.GORE_PRIORITY.isEmpty()) {
            flag = this.threshold.equals(priority);
        }
        // System.out.println(this.threshold.toString() + "|" + priority.toString() + "|" + flag);
        return flag;
    }
}
