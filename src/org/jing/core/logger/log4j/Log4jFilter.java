package org.jing.core.logger.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.jing.core.lang.Carrier;
import org.jing.core.lang.Configuration;
import org.jing.core.lang.itf.JInit;
import org.jing.core.logger.Log4jInit;
import org.jing.core.util.ClassUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-01-30 <br>
 */
public class Log4jFilter extends Filter implements JInit {
    private static final HashMap<Level, AbstractLog4jFilter> LEVEL_FILTER_MAP = new HashMap<Level, AbstractLog4jFilter>();

    private static Log4jClassifyPlugin PLUGIN_CLASSIFY = new Log4jClassifyPlugin();

    public Log4jFilter() {
    }

    @Override public int decide(LoggingEvent loggingEvent) {
        try {
            if (null != PLUGIN_CLASSIFY) {
                PLUGIN_CLASSIFY.execute(loggingEvent);
            }
            AbstractLog4jFilter log4jFilter = LEVEL_FILTER_MAP.get(loggingEvent.getLevel());
            if (null != log4jFilter && !log4jFilter.execute(loggingEvent)) {
                return -1;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void init(Carrier params) {
        try {
            Carrier loggerCarrier = Log4jInit.getParameter();
            if (loggerCarrier.getCount("level-filter") != 0) {
                Carrier extendCarrier = loggerCarrier.getCarrier("level-filter");
                HashMap<String, Object> extendMap = extendCarrier.getValueMap();
                for (Map.Entry<String, Object> entry : extendMap.entrySet()) {
                    String propertyKey = entry.getKey().toUpperCase();
                    String propertyValue = String.valueOf(entry.getValue());
                    Class<? extends AbstractLog4jFilter> filterClass$ = (Class<? extends AbstractLog4jFilter>) ClassUtil.loadClass(propertyValue);
                    Level level$ = Log4jLoggerLevel.LEVEL_MAPPING.get(propertyKey);
                    if (null != level$) {
                        AbstractLog4jFilter filterInstance$ = filterClass$.newInstance();
                        LEVEL_FILTER_MAP.put(level$, filterInstance$);
                    }
                }
            }
        }
        catch (Exception e) {
            System.err.println("JingException: [LOGGING-LOG4J],Failed to implement the filter in system.cfg");
            e.printStackTrace();
        }
    }
}
