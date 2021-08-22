package test;

import org.jing.core.lang.JingException;
import org.jing.core.lang.node.Format4Json;
import org.jing.core.lang.node.Format4XML;
import org.jing.core.lang.node.JingNode;
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
        JingNode parseJson = JingNode.parseJson(originalContent);
        String asXML = parseJson.asXML();
        String asJson = parseJson.asJson(new Format4Json());
        System.out.println(asJson);
        JingNode parseAsJson = JingNode.parseJson(asJson);
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
        JingNode node = JingNode.parseXML(content);
        Format4XML format = new Format4XML();
        format.setNeedHead(false);
        System.out.println(node.asXML(format));
        System.out.println(node.asXML(Format4XML.getZpFormat(false)));*/
    }
    public static void main(String[] args) throws JingException {
        new Demo4DynamicNode();
    }
}
