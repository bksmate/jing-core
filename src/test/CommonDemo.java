package test;

import org.jing.core.config.statics.StaticConfigFactory;
import org.jing.core.lang.Carrier;
import org.jing.core.util.FileUtil;
import org.jing.core.util.StringUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class CommonDemo {
    private CommonDemo() throws Exception {
        long millis = System.currentTimeMillis ();
        long sum = 0L; // uses Long, not long
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println (sum);
        System.out.println ((System.currentTimeMillis () - millis) / 1000);
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
