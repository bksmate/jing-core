package test;

import org.jing.core.lang.Pair3;
import org.jing.core.logger.help.LoggerUtil;
import org.jing.core.util.FileUtil;
import org.jing.core.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class CommonDemo {
    private CommonDemo() throws Exception {
        System.out.println(FileUtil.getGeneralFileSizeString(4467982336L));
        System.out.println(FileUtil.getFileSuffix("123456.jpg"));
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
