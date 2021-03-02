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
        String json = "{\"code\":\"0f72c0c84e6f722de6fb57f9feb3691e26545bc2991ffc290ed35271bb855499f4ec31a2fb28b685d72009aa29bc89352986c5848276839d\",\"taoInfoUrl\":\"http:\\/\\/h5api.m.taobao.com\\/h5\\/mtop.taobao.detail.getdetail\\/6.0\\/?ttid=2016%2540taobao_h5_2.0.0&ecode=0&data=%7B%22itemNumId%22%3A%22637070057321%22%7D&jsv=2.4.8&type=jsonp&AntiCreep=true&sign=76b24a81b49cc9ba82ec13e7280f2551&v=6.0&dataType=jsonp&AntiFlood=true&t=1614645764000&H5Request=true&callback=mtopjsonp1&api=mtop.taobao.detail.getdetail&isSec=0&appKey=12574478\"}";
        Carrier jsonC = CarrierUtil.jsonContent2Carrier(json);
        System.out.println(jsonC.asXML());
        String url = jsonC.getString("taoInfoUrl");

    }

    public static void main(String[] args) throws Exception {
        new Demo4Carrier();
    }
}
