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
        StaticConfigFactory.registerStaticConfig(TempStaticConfig.class, Carrier.parseXML(FileUtil.readFile("config/system.xml")));
        TempStaticConfig config = StaticConfigFactory.createStaticConfig(TempStaticConfig.class);
        System.out.println(config);
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
