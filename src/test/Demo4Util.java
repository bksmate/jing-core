package test;

import org.jing.core.util.FileUtil;
import org.jing.core.util.StringUtil;

import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-06-28 <br>
 */
public class Demo4Util {
    private Demo4Util() throws Exception {
        String testString = "中0123456789";
        System.out.println(StringUtil.cutString(testString, 4));
        FileUtil.writeFile("temp.txt", testString, "GBK");
        FileUtil.writeFile("temp.txt", testString, true);
        List<String> rowList = new ArrayList<String>();
        rowList.add(StringUtil.cutString(testString, 4));
        rowList.add(StringUtil.cutString(testString, 5));
        rowList.add(StringUtil.cutString(testString, 6));
        rowList.add(StringUtil.cutString(testString, 7));
        FileUtil.writeFile("temp.txt", rowList, true);
    }

    public static void main(String[] args) throws Exception {
        new Demo4Util();
    }
}
