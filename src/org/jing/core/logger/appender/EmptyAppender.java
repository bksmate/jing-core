package org.jing.core.logger.appender;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;

/**
 * Description: 缺省Appender, 完全依赖LoggerAppender的实现. <br>
 *
 * @author: bks <br>
 * @createDate: 2021-01-13 <br>
 */
public class EmptyAppender extends BaseAppender {
    public EmptyAppender(Carrier paramC) throws JingException {
        super(paramC);
    }
}
