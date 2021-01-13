package test;

import org.jing.core.logger.JingLogger;
import org.jing.core.logger.JingLoggerLevel;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-08-24 <br>
 */
public class Demo4JingLogger {
    private static final JingLogger LOGGER = JingLogger.getLogger(Demo4JingLogger.class);

    public Demo4JingLogger() {
        LOGGER.imp("imp1");
        JingLoggerLevel.IMP.getLevelConfig().setFormat("%m%n");
        LOGGER.imp("imp2");
    }
    public static void main(String[] args) {
        new Demo4JingLogger();
    }
}
