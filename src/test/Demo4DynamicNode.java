package test;

import org.jing.core.lang.JingException;
import org.jing.core.lang.Carrier;
import org.jing.core.format.Carrier2Json;
import org.jing.core.util.FileUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-21 <br>
 */
public class Demo4DynamicNode {
    private Demo4DynamicNode() throws JingException {
        String originalContent = FileUtil.readFile("temp/temp.json");
        Carrier parseJson = Carrier.parseJson(originalContent);
        String asXML = parseJson.asXML();
        String asJson = parseJson.asJson(new Carrier2Json());
        System.out.println(asJson);
        Carrier parseAsJson = Carrier.parseJson(asJson);
        System.out.println(parseAsJson.asXML());
        String value = parseAsJson.getStringByName(3, "service");
        System.out.println(value);
        value = parseAsJson.getStringByPath("object.flag1", "false");
        System.out.println(value);
        parseAsJson.setValueByPath("object.flag1", "hello world");
        value = parseAsJson.getStringByPath("object.flag1", "www");
        System.out.println(value);
        System.out.println(parseAsJson.asJson());
        System.out.println(parseAsJson.asXML());
        /*String content = FileUtil.readFile("temp/temp$1.xml");
        Carrier node = Carrier.parseXML(content);
        Carrier2XML format = new Carrier2XML();
        format.setNeedHead(false);
        System.out.println(node.asXML(format));
        System.out.println(node.asXML(Carrier2XML.getZpFormat(false)));*/
    }
    public static void main(String[] args) throws JingException {
        new Demo4DynamicNode();
    }
}
