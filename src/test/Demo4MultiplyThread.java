package test;

import org.jing.core.lang.Configuration;

import java.lang.Exception;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-05-27 <br>
 */
public class Demo4MultiplyThread {
    private Demo4MultiplyThread() throws Exception {
        Configuration.getInstance().reloadConfigFile();
    }

    public static void main(String[] args) throws Exception {
        new Demo4MultiplyThread();
    }
}
