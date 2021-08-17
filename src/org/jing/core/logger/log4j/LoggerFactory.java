package org.jing.core.logger.log4j;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.logger.JingLogger;
import org.jing.core.logger.itf.JingLoggerFactoryItf;
import org.jing.core.logger.sys.SingleLogger;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-16 <br>
 */
public class LoggerFactory implements JingLoggerFactoryItf {
    @Override public void init(Carrier param) throws JingException {
        SingleLogger.log("init Logger.LOG4J...");
    }

    @Override public boolean isEnable() {
        try {
            Class.forName("org.apache.log4j.Logger");
        }
        catch (Throwable t) {
            return false;
        }
        return true;
    }

    @Override
    public JingLogger getLogger(String name) {
        return new Logger(name);
    }

    @Override
    public JingLogger getLogger(Class clazz) {
        return new Logger(clazz);
    }
}
