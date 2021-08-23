package test;

import org.jing.core.lang.Carrier;
import org.jing.core.util.FileUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class Demo4Json {
    private Demo4Json() throws Exception {
        String jsonContent = FileUtil.readFile("temp/temp.json");
        Carrier jsonC = Carrier.parseJson(jsonContent);
        System.out.println(jsonC.asXML());
        String result = jsonC.asJson();
        System.out.println(result);
    }

    public static void main(String[] args) throws Exception {
        new Demo4Json();
    }
}
