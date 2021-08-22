package org.jing.core.lang;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jing.core.json.JsonFormat;
import org.jing.core.util.CarrierUtil;
import org.jing.core.util.StringUtil;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: A Special HashMap. <br>
 *
 * @author: lxd <br>
 * @createDate 2019-01-10 17:11:02 <br>
 */
public class Carrier {
    /**
     * Description: Value Map. <br>
     */
    private HashMap<String, Object> valueMap = new LinkedHashMap<String, Object>();

    private HashMap<String, String> extendMap = null;

    private String rootNodeName = Const.CARRIER_ROOT_NODE;

    /**
     * Description: Constructor, it will initialize the value map with an empty HashMap. <br>
     */
    public Carrier() {
    }

    public Carrier(String rootNodeName) {
        if (StringUtil.isNotEmpty(rootNodeName)) {
            this.rootNodeName = rootNodeName;
        }
    }

    /**
     * Description: Throw exception if value map is empty. <br>
     *
     * @author: bks. <br>
     * @throws JingException <br>
     */
    private void checkFeasibility() throws JingException {
        if (null == valueMap) {
            throw new JingException("null value map");
        }
    }

    /**
     * Description: Count how many entry with the same key. <br>
     *
     * @param key <br>
     * @return <br>
     * @throws JingException <br>
     */
    public int getCount(String key) throws JingException {
        checkFeasibility();
        return CarrierUtil.getCount(valueMap, key);
    }

    public void setRootNodeName(String rootNodeName) {
        this.rootNodeName = rootNodeName;
    }

    public String getRootNodeName() {
        return rootNodeName;
    }

    /**
     * Description: Setter. <br>
     *
     * @param valueMap <br>
     */
    public Carrier setValueMap(HashMap<String, Object> valueMap) {
        this.valueMap = valueMap;
        return this;
    }

    public Carrier addValueByKey(String key, Object value) throws JingException {
        CarrierUtil.addValueByKey(valueMap, key, value);
        return this;
    }

    public Carrier setValueByKey(int seq, String key, Object value) throws JingException {
        CarrierUtil.setValueByKey(valueMap, key, value, seq);
        return this;
    }

    public Carrier setValueByKey(String key, Object value) throws JingException {
        CarrierUtil.setValueByKey(valueMap, key, value, 0);
        return this;
    }

    public Object getValueByKey(int seq, String key, Object defaultValue) throws JingException {
        return CarrierUtil.getValueByKey(valueMap, key, defaultValue, seq);
    }

    public Object getValueByKey(String key, Object defaultValue) throws JingException {
        return CarrierUtil.getValueByKey(valueMap, key, defaultValue, 0);
    }

    public Object getValueByKey(int seq, String key) throws JingException {
        return CarrierUtil.getValueByKey(valueMap, key, null, seq);
    }

    public Object getValueByKey(String key) throws JingException {
        return CarrierUtil.getValueByKey(valueMap, key, null, 0);
    }

    public void removeByKey(String key) {
        this.valueMap.remove(key);
    }

    public String getString(int seq, String key, String defaultString) throws JingException {
        Object retObject = getValueByKey(seq, key, defaultString);
        return StringUtil.ifEmpty(StringUtil.parseString(retObject), defaultString);
    }

    public String getString(int seq, String key) throws JingException {
        Object retObject = getValueByKey(seq, key);
        return StringUtil.parseString(retObject);
    }

    public String getString(String key, String defaultString) throws JingException {
        Object retObject = getValueByKey(key, defaultString);
        return StringUtil.ifEmpty(StringUtil.parseString(retObject), defaultString);
    }

    public String getString(String key) throws JingException {
        Object retObject = getValueByKey(key);
        return StringUtil.parseString(retObject);
    }

    public Carrier getCarrier(String key, int seq) throws JingException {
        Object temp = getValueByKey(seq, key, null);
        if (null == temp) {
            throw new JingException("Invalid key: " + key);
        }
        if (temp instanceof Carrier) {
            return (Carrier) temp;
        }
        else if (temp instanceof HashMap) {
            Carrier tempC = new Carrier();
            try {
                tempC.valueMap = (HashMap<String, Object>) temp;
            }
            catch (Exception e) {
                tempC.valueMap = new HashMap<String, Object> ((Map<? extends String, ?>) temp);
            }
            return tempC;
        }
        else if (temp instanceof String && StringUtil.isEmpty((String) temp)) {
            return new Carrier();
        }
        else {
            throw new JingException("Invalid key: " + key);
        }
    }

    public Carrier getCarrier(String key) throws JingException {
        return getCarrier(key, 0);
    }

    public HashMap<String, Object> getValueMap() {
        return valueMap;
    }

    public HashMap<String, String> getExtendMap() {
        return extendMap;
    }

    public void extend(String key, String value) {
        if (null == extendMap) {
            extendMap = new LinkedHashMap<>();
        }
        extendMap.put(key, value);
    }

    public String extend(String key) {
        return StringUtil.getMapString(extendMap, key);
    }

    public void setServiceCode(String serviceCode) {
        this.extend("SERVICE_CODE", serviceCode);
    }

    public String getServiceCode() {
        return this.extend("SERVICE_CODE");
    }

    public Carrier putAll(Map<String, Object> valueMap) {
        this.valueMap.putAll(valueMap);
        return this;
    }

    public String asXML() {
        OutputFormat xmlFormat = CarrierUtil.generateXMLFormat(true, false, true, "    ");
        return asXML(xmlFormat, true);
    }

    public String asLightXML(boolean needHead) {
        OutputFormat xmlFormat = CarrierUtil.generateXMLFormat(true, false, false, null);
        return asXML(xmlFormat, needHead);
    }

    public String asXML(OutputFormat xmlFormat, boolean needHead) {
        Document document =DocumentHelper.createDocument();
        Element rootElement = DocumentHelper.createElement(rootNodeName);
        document.setRootElement(rootElement);
        // 设置扩展属性
        if (null != extendMap) {
            for (Map.Entry<String, String> entry$ : extendMap.entrySet()) {
                rootElement.addAttribute(entry$.getKey(), entry$.getValue());
            }
        }
        // 设置数据
        CarrierUtil.object2Element(valueMap, null, rootElement);
        try {
            StringWriter sw = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(sw, xmlFormat);
            if (needHead) {
                xmlWriter.write(document);
            }
            else {
                xmlWriter.write(rootElement);
            }
            return sw.toString();
        }
        catch (Exception e) {
            return null;
        }
    }

    public Object getValueByPath(String path) throws JingException {
        String[] paths = path.split("\\.");
        int length = paths.length;
        Object retObject;
        Carrier tempCarrier = this;
        for (int i$ = 0; i$ < length; i$++) {
            retObject = tempCarrier.getValueByKey(paths[i$]);
            if (i$ == length - 1) {
                return retObject;
            }
            if (null != retObject) {
                if (retObject instanceof HashMap || retObject instanceof Carrier) {
                    tempCarrier = tempCarrier.getCarrier(paths[i$]);
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        return null;
    }

    public String getStringByPath(String path) throws JingException {
        Object retObject = getValueByPath(path);
        if (null == retObject) {
            return null;
        }
        else {
            return String.valueOf(retObject);
        }
    }

    public String getStringByPath(String path, String defaultString) throws JingException {
        return StringUtil.ifEmpty(getStringByPath(path), defaultString);
    }

    public void setValueByPath(String path, Object value) throws JingException {
        String[] paths = path.split("\\.");
        int length = paths.length;
        Carrier p = this;
        Object o;
        String key = paths[0];
        for (int i$ = 1; i$ < length; i$++) {
            o = p.getValueByKey(key);
            if (o instanceof Carrier) {
                p = (Carrier) o;
            }
            else if (o instanceof Map) {
                Carrier tempC = new Carrier();
                tempC.putAll((Map<String, Object>) o);
                p.setValueByKey(key, tempC);
                p = tempC;
            }
            else if (o instanceof List) {
                Carrier tempC = new Carrier();
                if (((List) o).size() > 1) {
                    ((List) o).set(0, tempC);
                }
                else {
                    p.setValueByKey(key, tempC);
                }
                p = tempC;
            }
            else {
                Carrier tempC = new Carrier();
                p.setValueByKey(key, tempC);
                p = tempC;
            }
            key = paths[i$];
        }
        p.setValueByKey(key, value);
    }

    public String asJson() {
        return CarrierUtil.carrier2JsonContent(this);
    }

    public String asJson(JsonFormat format) {
        return CarrierUtil.carrier2JsonContent(this, format);
    }

    public String asJson(String newline, String indent) {
        return CarrierUtil.carrier2JsonContent(this, new JsonFormat(newline, indent));
    }

    public String asJson(String newline, boolean needIndent) {
        return CarrierUtil.carrier2JsonContent(this, new JsonFormat(newline, needIndent));
    }

    public String asJson(boolean needNewline, String indent) {
        return CarrierUtil.carrier2JsonContent(this, new JsonFormat(needNewline, indent));
    }

    public String asJson(boolean needNewline, boolean needIndent) {
        return CarrierUtil.carrier2JsonContent(this, new JsonFormat(needNewline, needIndent));
    }

    public static Carrier parseJson(String json) throws JingException {
        return CarrierUtil.jsonContent2Carrier(json);
    }

    public static Carrier parseXML(String xml) throws JingException {
        return CarrierUtil.string2Carrier(xml);
        /*try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            factory.setIgnoringComments(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(xml.getBytes());
            org.w3c.dom.Document document = builder.parse(stream);
            org.w3c.dom.Element element = document.getDocumentElement();
            return parseXML(element);
        }
        catch (Throwable t) {
            throw new JingException(t, "failed to parse by xml content");
        }*/
    }

    /*public static Carrier parseXML(Node node) throws JingException {
        Carrier carrier = new Carrier(node.getNodeName());
        carrier.parseXML(node, null);
        return carrier;
    }

    private void parseXML(Node node, String name) throws JingException {
        if (isNodeTextOnly(node)) {
            this.addValueByKey(name, getNodeText(node));
        }
        else {
            Node childNode;
            Carrier childCarrier;
            NodeList nodeList = node.getChildNodes();
            int length = null == nodeList ? 0 : nodeList.getLength();
            childCarrier = new Carrier();
            NamedNodeMap attrMap = node.getAttributes();
            if (null != attrMap) {
                int size = attrMap.getLength();
                Node attrNode;
                for (int i$ = 0; i$ < size; i$++) {
                    attrNode = attrMap.item(i$);
                    childCarrier.extend(attrNode.getNodeName(), StringUtil.ifEmpty(attrNode.getNodeValue()));
                }
            }
            for (int i$ = 0; i$ < length; i$++) {
                childNode = nodeList.item(i$);
                if (childNode.getNodeType() == Node.TEXT_NODE) {
                    continue;
                }
                childCarrier.parseXML(childNode, childNode.getNodeName());
            }
            if (null == name) {
                this.valueMap = childCarrier.valueMap;
            }
            else {
                this.addValueByKey(name, childCarrier);
            }
        }
        /*
        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE: {
                NodeList nodeList = node.getChildNodes();
                int length = null == nodeList ? 0 : nodeList.getLength();
                if (length == 0) {
                    this.setValueByKey(node.getNodeName(), null);
                }
                else {
                    Carrier childCarrier;
                    Carrier p;
                    Node childNode;
                    for (int i$ = 0; i$ < length; i$++) {
                        childNode = nodeList.item(i$);
                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            p = new Carrier();
                        }
                        else {
                            p = this;
                        }
                        p.parseXML(childNode, node.getNodeName());
                        if (p != this) {
                            p.addValueByKey(childNode.getNodeName(), p);
                        }
                        else {
                            this.setValueMap(p.getValueMap());
                        }
                    }
                }
                break;
            }
            case Node.TEXT_NODE: {
                if (node.getPreviousSibling() == null && node.getNextSibling() == null) {
                    this.addValueByKey(name, StringUtil.ifEmpty(node.getNodeValue()).trim());
                }
                break;
            }
            default:
                throw new JingException("invalid node type: {}", node.getNodeType());
        }
    }

    private boolean isNodeTextOnly(Node node) throws JingException {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            throw new JingException("invalid node type");
        }
        NodeList childNodeList = node.getChildNodes();
        int size = null == childNodeList ? 0 : childNodeList.getLength();
        return size == 0 || size == 1 && childNodeList.item(0).getNodeType() == Node.TEXT_NODE;
    }

    private String getNodeText(Node node) {
        NodeList childNodeList = node.getChildNodes();
        int size = null == childNodeList ? 0 : childNodeList.getLength();
        if (0 == size) {
            return "";
        }
        return StringUtil.ifEmpty(childNodeList.item(0).getNodeValue()).trim();
    }*/

    @Override
    public String toString() {
        return null == valueMap ? null : valueMap.toString();
    }
}
