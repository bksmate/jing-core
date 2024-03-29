package test;

import org.jing.core.config.statics.StaticConfigFactory;
import org.jing.core.lang.Carrier;
import org.jing.core.lang.ExceptionHandler;
import org.jing.core.lang.JingException;
import org.jing.core.lang.JingExtraException;
import org.jing.core.util.FileUtil;
import org.jing.core.util.StringUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class CommonDemo {
    private CommonDemo() throws Exception {
        String content = "\\123\\456\"";
        System.out.println(content);
        System.out.println(content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\""));
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
