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
        /*String jsonContent = FileUtil.readFile("temp/temp.json");
        Carrier jsonC = Carrier.parseJson(jsonContent);
        System.out.println(jsonC.asXML());
        String result = jsonC.asJson();
        System.out.println(result);*/

        Carrier jsonC = new Carrier();
        jsonC.setValueByPath("temp.test1", "123\"");
        jsonC.setValueByPath("temp.test2", "123\\");
        jsonC.setValueByPath("temp.test3", "123\n");
        jsonC.setValueByPath("temp.test4", "123\t");
        jsonC.setValueByPath("temp.test5", "123\r");
        System.out.println(jsonC.asXML());
        System.out.println(jsonC.asJson());
        System.out.println(jsonC.getStringByPath("temp.test1"));
    }

    public static void main(String[] args) throws Exception {
        new Demo4Json();
    }
}
