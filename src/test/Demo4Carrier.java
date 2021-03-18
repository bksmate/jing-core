package test;

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
        String json = FileUtil.readFile("temp/temp.json");
        System.out.println(json);
        Carrier carrier = Carrier.parseJson(json);
        System.out.println(carrier.asXML());
        System.out.println(carrier.asJson());

    }

    public static void main(String[] args) throws Exception {
        new Demo4Carrier();
    }
}
