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
        LOGGER.debug("中文debug");
        LOGGER.info("中文info");
        LOGGER.imp("中文imp");
        LOGGER.warn("中文warn");
        LOGGER.sql("中文sql", 1);
        LOGGER.error("中文err");
    }
    public static void main(String[] args) {
        new Demo4JingLogger();
    }
}
