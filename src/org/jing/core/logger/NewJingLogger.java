package org.jing.core.logger;

import org.jing.core.lang.Configuration;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-31 <br>
 */
public class NewJingLogger {
    private String name;

    private NewJingLogger() {}

    public static NewJingLogger getLogger(Class clazz) {
        NewJingLogger logger = new NewJingLogger();
        logger.name = clazz.getName();
        return logger;
    }

    public static NewJingLogger getLogger(String name) {
        NewJingLogger logger = new NewJingLogger();
        logger.name = name;
        return logger;
    }

    public void log(JingLoggerLevel level, String msg, Object... objects) {
        if (level.isGreaterOrEquals(JingLoggerConfiguration.rootLevel)) {

        }
    }

    public static void main(String[] args) {
        Configuration.getInstance();
        NewJingLogger logger = NewJingLogger.getLogger(NewJingLogger.class);
        logger.log(JingLoggerLevel.ALL, "test: {}", 123);
    }
}
