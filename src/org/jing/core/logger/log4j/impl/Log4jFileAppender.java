package org.jing.core.logger.log4j.impl;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.jing.core.logger.log4j.Log4jLoggerLevel;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-09-22 <br>
 */
public class Log4jFileAppender extends FileAppender {
    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        if (!this.threshold.equals(priority)) {
            return false;
        }
        if (Log4jLoggerLevel.EQUALS_EMPTY && Log4jLoggerLevel.GORE_EMPTY && Log4jLoggerLevel.LORE_EMPTY && Log4jLoggerLevel.IGNORE_EMPTY) {
            return this.threshold.equals(priority);
        }
        boolean flag;
        // check equals.
        flag = !Log4jLoggerLevel.EQUALS_EMPTY && Log4jLoggerLevel.EQUALS_PRIORITY.contains(priority);
        // check ignore.
        if (flag && !Log4jLoggerLevel.IGNORE_EMPTY) {
            flag = !Log4jLoggerLevel.IGNORE_PRIORITY.contains(priority);
        }
        // check greater or equals.
        if (flag && !Log4jLoggerLevel.GORE_EMPTY) {
            for (Level level : Log4jLoggerLevel.GORE_PRIORITY) {
                flag = priority.isGreaterOrEqual(level);
                break;
            }
        }
        // check less or equals.
        if (flag && !Log4jLoggerLevel.LORE_EMPTY) {
            for (Level level : Log4jLoggerLevel.LORE_PRIORITY) {
                flag = !priority.isGreaterOrEqual(level) || priority.equals(level);
                break;
            }
        }
        return flag;
    }
}
