package test;

import org.jing.core.lang.Carrier;
import org.jing.core.util.CarrierUtil;
import org.jing.core.util.FileUtil;

import java.lang.Exception;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class CommonDemo {
    private CommonDemo() throws Exception {
        String path = "E:\\W\\WorkSpace\\idea\\jing-jdbc\\out\\artifacts\\jing_jdbc_jar";
        path = "/opt/s";
        System.out.println(path.indexOf(":") != -1 || path.startsWith("\\") || path.startsWith("/"));
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
