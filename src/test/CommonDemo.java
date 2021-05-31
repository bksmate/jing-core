package test;

import org.jing.core.util.DateUtil;

import java.util.Calendar;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class CommonDemo {
    private CommonDemo() throws Exception {
        System.out.println(DateUtil.getTimeBetween("20210524", "yyyyMMdd", "2021052301", "yyyyMMddHH", Calendar.DATE));
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
