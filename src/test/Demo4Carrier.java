package test;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.Const;
import org.jing.core.util.CarrierUtil;

import java.lang.Exception;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-06-29 <br>
 */
public class Demo4Carrier {
    private Demo4Carrier() throws Exception {
        Carrier carrier = CarrierUtil.string2Carrier(Const.SYSTEM_DEFAULT_CONFIG);
        Carrier newC = new Carrier();
        newC.putAll(carrier.getValueMap());
        System.out.println(newC.asXML());
    }

    public static void main(String[] args) throws Exception {
        new Demo4Carrier();
    }
}
