package test;

import org.jing.core.lang.Carrier;
import org.jing.core.util.CarrierUtil;

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
        Carrier subCarrier = new Carrier();
        subCarrier.addValueByKey("ROW", 1);
        subCarrier.addValueByKey("ROW", "2");
        subCarrier.addValueByKey("ROW", "3");
        carrier.addValueByKey("sub", subCarrier);
        System.out.println(carrier.asXML());
        String jsonContent = CarrierUtil.carrier2JsonContent(carrier);
        System.out.println(jsonContent);
        Carrier jsonCarrier = CarrierUtil.jsonContent2Carrier(jsonContent, null);
        System.out.println(jsonCarrier.asXML());
        System.out.println(CarrierUtil.carrier2JsonContent(jsonCarrier));
    }

    public static void main(String[] args) throws Exception {
        new Demo4Carrier();
    }
}
