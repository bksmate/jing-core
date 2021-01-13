package test;

import org.jing.core.lang.Pair3;
import org.jing.core.logger.help.LoggerUtil;
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
        String msg = "sql.125124.log";
        Pair3<String, String, String> p = LoggerUtil.analysisFileName(msg);
        String regex = p.getA() + "\\.\\d+\\." + p.getB();
        System.out.println(regex);
        System.out.println(msg.matches(regex));
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
