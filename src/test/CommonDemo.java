package test;

import org.jing.core.logger.JingLogger;
import org.jing.core.util.StringUtil;

import java.lang.Exception;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class CommonDemo {
    private CommonDemo() throws Exception {
        JingLogger logger = JingLogger.getLogger(CommonDemo.class);
        logger.sql("SELECT 1 FROM DUAL", this.hashCode());
        logger.sql("SELECT 1 FROM DUAL", "1, 2, 3", this.hashCode());
        logger.sql("{} row selected.", this.hashCode(), 1);
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
