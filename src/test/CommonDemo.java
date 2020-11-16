package test;

import netscape.javascript.JSObject;
import org.jing.core.lang.Carrier;
import org.jing.core.lang.Const;
import org.jing.core.logger.JingLogger;
import org.jing.core.util.CarrierUtil;
import org.jing.core.util.FileUtil;
import org.jing.core.util.GenericUtil;
import org.jing.core.util.StringUtil;

import java.io.OutputStream;
import java.lang.Exception;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class CommonDemo {
    private static final JingLogger LOGGER = JingLogger.getLogger(CommonDemo.class);
    private CommonDemo() throws Exception {
        // char split = 30;
        // System.out.println("T18200000197" + split + "9621" + split + "ZZDH" + split + "40" + split + "201109316282" + split + "TH18bB00000200000521" + split + "0000" + split + "交易成功" + split + "20201109170408" + split + "20201111111219");
        /*List<String> list = FileUtil.readFile2List("H:\\D\\Documents\\Desktop\\socket.log");
        int count = GenericUtil.countList(list);
        String content;
        for (int i$ = 0; i$ < count; i$++) {
            content = list.get(i$).trim();
            if (StringUtil.isEmpty(content)) {
                continue;
            }
            LOGGER.imp(content);
            Socket socket = new Socket("127.0.0.1", 40011);
            OutputStream writer = socket.getOutputStream();
            writer.write(list.get(i$).getBytes("gbk"));
            writer.flush();
            socket.shutdownOutput();
            socket.close();
            Thread.sleep(new Random().nextInt(2000));
        }*/
        System.out.println(Const.SYSTEM_DEFAULT_LOG4J_CONFIG);
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
