package test;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.lang.JingExtraException;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class CommonDemo {
    private CommonDemo() throws Exception {
        try {
            Carrier carrier = new Carrier();
            Carrier.class.getMethod("getCarrier", String.class).invoke(carrier, "123");
        }
        catch (Throwable t) {
            throw new JingExtraException(t, "E003", "Msg");
        }
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
