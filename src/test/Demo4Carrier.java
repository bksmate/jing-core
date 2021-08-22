package test;

import org.jing.core.json.JsonFormat;
import org.jing.core.lang.Carrier;
import org.jing.core.lang.Const;
import org.jing.core.util.CarrierUtil;
import org.jing.core.util.FileUtil;

import java.lang.Exception;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-06-29 <br>
 */
public class Demo4Carrier {
    private Demo4Carrier() throws Exception {
        String xml = FileUtil.readFile("temp/temp$1.xml");
        Carrier xmlC = Carrier.parseXML(xml);
        System.out.println(xmlC.asXML());

    }

    public static void main(String[] args) throws Exception {
        new Demo4Carrier();
    }
}
