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
        for (int i$ = 0; i$ < 100; i$++) {
            final int finalI$ = i$;
            Runnable thread = new Runnable() {
                @Override
                public void run() {
                    LOGGER.debug(finalI$);
                }
            };
            new Thread(thread).start();
        }
    }
    public static void main(String[] args) {
        new Demo4JingLogger();
    }
}
