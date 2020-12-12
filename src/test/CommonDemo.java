package test;

import org.jing.core.lang.Configuration;
import org.jing.core.lang.ExceptionHandler;
import org.jing.core.lang.JingExtraException;
import org.jing.core.logger.JingLogger;

import java.lang.Exception;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class CommonDemo {
    private static final JingLogger LOGGER = JingLogger.getLogger(CommonDemo.class);
    private CommonDemo() throws Exception {

        Configuration.getInstance().reloadConfigFile();
        ExceptionHandler.publish("9999", "errMsg: {}", "message");
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
