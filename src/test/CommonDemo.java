package test;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jing.core.util.DateUtil;
import org.jing.core.util.FileUtil;
import org.jing.core.util.GenericUtil;

import java.util.Calendar;
import java.util.List;
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
        FileUtil.zip("temp.zip", "src", true);
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
