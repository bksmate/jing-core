package test;

import org.jing.core.util.StringUtil;

import java.lang.Exception;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-06-28 <br>
 */
public class Demo4Util {
    private Demo4Util() throws Exception {
        String testString = "0123456789";
        System.out.println(StringUtil.cutString(testString, 4));

    }

    public static void main(String[] args) throws Exception {
        new Demo4Util();
    }
}
