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
        Carrier crawlerCarrier = CarrierUtil.string2Carrier(FileUtil.readFile("F:\\W\\WorkSpace\\IDEA\\Crawler/config/crawler.xml"));
        String header = crawlerCarrier.getStringByPath("request-header");
        System.out.println(header);
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
