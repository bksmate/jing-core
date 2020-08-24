package test;

import org.jing.core.logger.JingLogger;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-08-24 <br>
 */
public class Demo4JingLogger {
    private static final JingLogger LOGGER = JingLogger.getLogger(Demo4JingLogger.class);

    public Demo4JingLogger() {
        LOGGER.debug("debug");
        LOGGER.imp("imp");
        LOGGER.sql("sql", 1);
        LOGGER.error("err");
        LOGGER.log(TempLoggerLevel.ABC, "??");
    }
    public static void main(String[] args) {
        new Demo4JingLogger();
    }
}
