package test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.Exception;

import org.jing.core.lang.JingException;
import org.jing.core.util.FileUtil;
import org.jing.core.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-20 <br>
 */
public class Demo4XML {
    private Demo4XML() throws Exception {
        String content = FileUtil.readFile("temp/temp$1.xml");
        System.out.println(content);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(content.getBytes());
        Document document = builder.parse(stream);
        Element element = document.getDocumentElement();
        // parse(element, 0);
    }

    /*private void parse(Node node, int level) throws JingException {
        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                System.out.println();
                NodeList nodeList = node.getChildNodes();
                int length = nodeList.getLength();
                if (length == 0) {
                    System.out.print(StringUtil.repeat("    ", level) + node.getNodeName() + " : ");
                }
                else  {
                    System.out.print(StringUtil.repeat("    ", level) + node.getNodeName() + " : ");
                    for (int i$ = 0; i$ < length; i$++) {
                        parse(nodeList.item(i$), level + 1);
                    }
                }
                break;
            case Node.TEXT_NODE:
                if (node.getPreviousSibling() == null && node.getNextSibling() == null) {
                    System.out.print(node.getNodeValue().trim());
                }
                break;
            default:
                throw new JingException("invalid node type: {}", node.getNodeType());
        }
    }*/

    public static void main(String[] args) throws Exception {
        new Demo4XML();
    }
}
