package test;

import org.jing.core.lang.Carrier;

import java.lang.Exception;
import java.util.HashMap;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-06-29 <br>
 */
public class Demo4Carrier {
    private Demo4Carrier() throws Exception {
        Carrier carrier = new Carrier();
        carrier.addValueByKey("ROW", "1");
        carrier.addValueByKey("ROW", "2");
        carrier.addValueByKey("ROW", "3");
        System.out.println(carrier.asXML());
    }

    public static void main(String[] args) throws Exception {
        new Demo4Carrier();
    }
}
