package org.jing.core.logger.log4j;

import org.apache.log4j.spi.LoggingEvent;
import org.jing.core.lang.JingException;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-01-31 <br>
 */
public abstract class AbstractLog4jFilter {
    public abstract boolean execute(LoggingEvent loggingEvent) throws JingException;
}
