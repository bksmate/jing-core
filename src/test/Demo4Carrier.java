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
        Carrier carrier = new Carrier();
        carrier.setValueByKey("data", "files\\upload\\QQ图片20180822172522.jpg");
        System.out.println(CarrierUtil.carrier2JsonContent(carrier));

    }

    public static void main(String[] args) throws Exception {
        new Demo4Carrier();
    }
}
