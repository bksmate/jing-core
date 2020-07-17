package test;

import org.jing.core.util.StringUtil;

import java.lang.Exception;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class CommonDemo {
    private CommonDemo() throws Exception {
        System.out.println(StringUtil.pad("123", StringUtil.PAD_MODEL_LEFT, '0', 1));
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
