package test;

import org.jing.core.lang.Carrier;
import org.jing.core.util.FileUtil;

import java.lang.Exception;
import java.util.List;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-06-29 <br>
 */
public class Demo4Carrier {
    private Demo4Carrier() throws Exception {
        String xml = FileUtil.readFile("temp/temp.xml");
        Carrier xmlC = Carrier.parseXML(xml);
        xmlC.setValueByPath("USER_NAME.temp$", "<123>");
        System.out.println(xmlC.asXML());
        System.out.println(xmlC.toString());
        System.out.println(xmlC.getValueChildList());
    }

    public static void main(String[] args) throws Exception {
        new Demo4Carrier();
    }
}
