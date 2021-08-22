package org.jing.core.lang.node;

import org.jing.core.json.CharReader;
import org.jing.core.json.Token;
import org.jing.core.json.TokenList;
import org.jing.core.json.TokenType;
import org.jing.core.json.Tokenizer;
import org.jing.core.lang.Const;
import org.jing.core.lang.JingException;
import org.jing.core.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-21 <br>
 */
@SuppressWarnings({ "unused", "WeakerAccess", "UnusedReturnValue", "Duplicates" }) public class JingNode {
    private volatile String nodeName = "";

    private volatile List<JingNode> childNodeList = new ArrayList<>();

    private volatile Object nodeValue = null;

    private LinkedHashMap<String, String> attrMap = null;

    public JingNode() {
        nodeName = Const.CARRIER_ROOT_NODE;
    }

    public JingNode(String name) {
        this.setName(name);
        this.setValue(null);
    }

    public JingNode(String name, Object value) {
        this.setName(name);
        this.setValue(value);
    }

    public JingNode(String name, JingNode node) throws JingException {
        this.setName(name);
        this.addNode(node);
    }

    public JingNode setName(String name) {
        this.nodeName = name;
        return this;
    }

    public JingNode setValue(Object value) {
        this.nodeValue = value;
        return this;
    }

    public Object getValue() {
        return this.nodeValue;
    }

    public String getString() {
        return null == nodeValue ? "" : StringUtil.parseString(this.nodeValue);
    }

    public JingNode addNode(JingNode node) throws JingException {
        if (null == node) {
            throw new JingException("cannot add null node into list");
        }
        this.childNodeList.add(node);
        return this;
    }

    public JingNode removeNodeByName(String name) {
        JingNode node;
        int size = childNodeList.size();
        for (int i$ = 0; i$ < size; i$++) {
            node = childNodeList.get(i$);
            if (node.nodeName.equals(name)) {
                childNodeList.remove(i$);
                i$ --;
                size --;
            }
        }
        return this;
    }

    public JingNode removeNodeByName(String name, int seq) throws JingException {
        if (seq < 0) {
            throw new JingException("invalid seq: {}", seq);
        }
        int count = -1;
        JingNode node;
        int size = childNodeList.size();
        for (int i$ = 0; i$ < size; i$++) {
            node = childNodeList.get(i$);
            if (node.nodeName.equals(name)) {
                count ++;
            }
            if (count == seq) {
                childNodeList.remove(i$);
                break;
            }
        }
        return this;
    }

    public JingNode addValueByName(String name, Object value) {
        JingNode childNode = new JingNode();
        childNode.nodeName = name;
        childNode.nodeValue = value;
        childNodeList.add(childNode);
        return this;
    }

    public JingNode setValueByName(int seq, String name, Object value) throws JingException {
        if (seq < 0) {
            throw new JingException("invalid seq: {}", seq);
        }
        int count = -1;
        for (JingNode node : childNodeList) {
            if (null == node) {
                continue;
            }
            if (node.nodeName.equals(name)) {
                count++;
            }
            if (count == seq) {
                node.setValue(value);
                return this;
            }
        }
        return addValueByName(name, value);
    }

    public JingNode setValueByName(String name, Object value) throws JingException {
        return setValueByName(0, name, value);
    }

    public JingNode setValueByPath(String path, Object value) throws JingException {
        String[] names = path.split("\\.");
        JingNode node = this;
        int index = 0;
        String name;
        boolean flag;
        while (index < names.length) {
            name = names[index];
            flag = false;
            for (JingNode childNode : node.childNodeList) {
                if (name.equals(childNode.nodeName)) {
                    index ++;
                    node = childNode;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                JingNode newNode = new JingNode(name);
                node.addNode(newNode);
                node = newNode;
                index ++;
            }
            if (names.length == index) {
                node.nodeValue = value;
            }
        }
        return this;
    }

    public JingNode attr(String name, String value) {
        if (null == attrMap) {
            attrMap = new LinkedHashMap<>();
        }
        this.attrMap.put(name, value);
        return this;
    }

    public String attr(String name) {
        return this.attrMap.get(name);
    }

    public List<JingNode> getList(String name) {
        ArrayList<JingNode> retList = null;
        for (JingNode node : childNodeList) {
            if (node.nodeName.equals(name)) {
                if (null == retList) {
                    retList = new ArrayList<>();
                }
                retList.add(node);
            }
        }
        return retList;
    }

    public JingNode getNode(String name, int seq) {
        int count = -1;
        for (JingNode node : childNodeList) {
            if (node.nodeName.equals(name)) {
                count ++;
            }
            if (count == seq) {
                return node;
            }
        }
        return null;
    }

    public int getCount(String name) {
        int count = 0;
        for (JingNode node : childNodeList) {
            if (node.nodeName.equals(name)) {
                count ++;
            }
        }
        return count;
    }

    public Object getValueByName(int seq, String name, Object defaultValue) throws JingException {
        if (seq < 0) {
            throw new JingException("invalid seq: {}", seq);
        }
        int count = -1;
        Object retValue = null;
        for (JingNode node : childNodeList) {
            if (null == node) {
                continue;
            }
            if (node.nodeName.equals(name)) {
                count++;
            }
            if (count == seq) {
                retValue =  node.getValue();
                break;
            }
        }
        if (null == retValue) {
            return defaultValue;
        }
        else {
            return retValue;
        }
    }

    public Object getValueByName(int seq, String name) throws JingException {
        return getValueByName(seq, name, null);
    }

    public Object getValueByName(String name, Object defaultValue) throws JingException {
        return getValueByName(0, name, defaultValue);
    }

    public Object getValueByName(String name) throws JingException {
        return getValueByName(0, name, null);
    }

    public Object getValueByPath(String path, Object defaultValue) {
        String[] names = path.split("\\.");
        JingNode node = this;
        int index = 0;
        String name;
        boolean flag;
        while (index < names.length) {
            name = names[index];
            flag = false;
            for (JingNode childNode : node.childNodeList) {
                if (name.equals(childNode.nodeName)) {
                    index ++;
                    node = childNode;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return defaultValue;
            }
            if (names.length == index) {
                break;
            }
        }
        return node.nodeValue;
    }

    public Object getValueByPath(String path) {
        return getValueByPath(path, null);
    }

    public String getStringByPath(String path, String defaultString) {
        String[] names = path.split("\\.");
        JingNode node = this;
        int index = 0;
        String name;
        boolean flag;
        while (index < names.length) {
            name = names[index];
            flag = false;
            for (JingNode childNode : node.childNodeList) {
                if (name.equals(childNode.nodeName)) {
                    index ++;
                    node = childNode;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return defaultString;
            }
            if (names.length == index) {
                break;
            }
        }
        return StringUtil.parseString(node.nodeValue);
    }

    public String getStringByPath(String path) {
        return getStringByPath(path, null);
    }

    public String getStringByName(int seq, String name, String defaultString) throws JingException {
        return StringUtil.parseString(getValueByName(seq, name, defaultString));
    }

    public String getStringByName(int seq, String name) throws JingException {
        return getStringByName(seq, name, "");
    }

    public String getStringByName(String name, String defaultString) throws JingException {
        return getStringByName(0, name, defaultString);
    }

    public String getStringByName(String name) throws JingException {
        return getStringByName(0, name, "");
    }

    public String asXML() throws JingException {
        return asXML(new Format4XML());
    }

    public String asXML(Format4XML format) throws JingException {
        StringBuilder stbr = new StringBuilder();
        if (0 == format.getLevel()) {
            stbr.append(format.getHead());
        }
        else {
            stbr.append(format.getCurrentIndent());
        }
        stbr.append("<").append(nodeName);
        if (null != attrMap && format.isNeedNodeAttr())  {
            for (Map.Entry<String, String> attr : attrMap.entrySet()) {
                validateAttr(attr);
                stbr.append(String.format(" %s=\"%s\"", attr.getKey(), StringUtil.ifEmpty(attr.getValue())));
            }
        }
        String content = getString();
        if (childNodeList.isEmpty() && StringUtil.isEmpty(content)) {
            if (format.isExpandEmptyNode()) {
                stbr.append("></").append(nodeName).append(">");
            }
            else {
                stbr.append("/>");
            }
        }
        else if (!childNodeList.isEmpty()) {
            stbr.append(">");
            format.addLevel();
            for (JingNode node : childNodeList) {
                if (null == node) {
                    continue;
                }
                stbr.append(format.getNewLine()).append(node.asXML(format));
            }
            format.reduceLevel();
            stbr.append(format.getNewLine()).append(format.getCurrentIndent()).append("</").append(nodeName).append(">");
        }
        else {
            stbr.append(">").append(content).append("</").append(nodeName).append(">");
        }
        return stbr.toString();
    }

    public static JingNode parseXML(String xmlContent) throws JingException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            factory.setIgnoringComments(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(xmlContent.getBytes());
            Document document = builder.parse(stream);
            Element element = document.getDocumentElement();
            return parseXML(element);
        }
        catch (Throwable t) {
            throw new JingException(t, "failed to parse by xml content");
        }
    }

    public static JingNode parseXML(Node node) throws JingException {
        JingNode jingNode = new JingNode(node.getNodeName());
        jingNode.parseXML(node, 0);
        return jingNode;
    }

    private boolean parseXML(Node node, int level) throws JingException {
        NamedNodeMap attrMap = node.getAttributes();
        if (null != attrMap) {
            int size = attrMap.getLength();
            Node attrNode;
            for (int i$ = 0; i$ < size; i$++) {
                attrNode = attrMap.item(i$);
                this.attr(attrNode.getNodeName(), StringUtil.ifEmpty(attrNode.getNodeValue()));
            }
        }
        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE: {
                NodeList nodeList = node.getChildNodes();
                int length = nodeList.getLength();
                if (length == 0) {
                    this.setValue("");
                }
                else {
                    JingNode childJingNode;
                    Node childNode;
                    for (int i$ = 0; i$ < length; i$++) {
                        childNode = nodeList.item(i$);
                        if (childNode.getNodeType() == Node.TEXT_NODE) {
                            if (this.parseXML(childNode, level + 1)) {
                                return true;
                            }
                        }
                        else {
                            childJingNode = new JingNode(childNode.getNodeName());
                            if (childJingNode.parseXML(childNode, level + 1)) {
                                this.addNode(childJingNode);
                            }
                        }
                    }
                    /*if (length == 1 & (childNode = nodeList.item(0)).getNodeType() == Node.TEXT_NODE) {
                        this.setValue(StringUtil.ifEmpty(childNode.getNodeValue()).trim());
                    }
                    else {
                    }*/
                }
                return true;
            }
            case Node.TEXT_NODE: {
                if (node.getPreviousSibling() == null && node.getNextSibling() == null) {
                    this.setValue(StringUtil.ifEmpty(node.getNodeValue()).trim());
                    return true;
                }
                break;
            }
            default:
                throw new JingException("invalid node type: {}", node.getNodeType());
        }
        return false;
    }

    public String asJson() {
        return asJson(new Format4Json());
    }

    public String asJson(Format4Json format) {
        format.clearStringBuilder();
        if (format.isNeedRootNode()) {
            format.append("{");
            key2Json(nodeName, format);
            value2Json(this, format);
            format.append("}");
        }
        else {
            value2Json(this, format);
        }
        return format.getStringBuilder().toString();
    }

    private void key2Json(String name, Format4Json format) {
        format.append(format.getNewline()).append(format.getCurrentIndent())
            .append("\"").append(Format4Json.escape(name)).append("\":").append(format.getSpace());
    }

    private void value2Json(Object value, Format4Json format) {
        if (childNodeList.isEmpty()) {
            if (null == nodeValue) {
                format.append("null");
            }
            else if (nodeValue instanceof Integer) {
                format.append(String.valueOf(nodeValue));
            }
            else if (nodeValue instanceof Long) {
                format.append(nodeValue + "L");
            }
            else if (nodeValue instanceof Float) {
                format.append(nodeValue + "F");
            }
            else if (nodeValue instanceof Double) {
                format.append(nodeValue + "D");
            }
            else if (nodeValue instanceof Boolean) {
                format.append(String.valueOf(nodeValue));
            }
            else {
                format.append("\"").append(StringUtil.parseString(nodeValue)).append("\"");
            }
        }
        else {
            format.append("{");
            format.addLevel();
            int size = childNodeList.size();
            int[] posArr = new int[size];
            JingNode node$i, node$j;
            boolean flag = false;
            boolean flag$;
            for (int i$ = 0; i$ < size; i$++) {
                if (0 != posArr[i$]) {
                    continue;
                }
                if (flag) {
                    format.append(",").append(format.getSpace());
                }
                else {
                    flag = true;
                }
                flag$ = false;
                node$i = childNodeList.get(i$);
                key2Json(node$i.nodeName, format);
                posArr[i$] = 1;
                for (int j$ = i$ + 1; j$ < size; j$++) {
                    node$j = childNodeList.get(j$);
                    if (node$i.nodeName.equals(node$j.nodeName)) {
                        posArr[j$] = 1;
                        if (!flag$) {
                            flag$ = true;
                            format.append("[");
                            format.addLevel();
                            format.append(format.getNewline()).append(format.getCurrentIndent());
                            node$i.value2Json(node$i.nodeValue, format);
                        }
                        format.append(",").append(format.getSpace());
                        format.append(format.getNewline()).append(format.getCurrentIndent());
                        node$j.value2Json(node$j.nodeValue, format);
                    }
                }
                if (flag$) {
                    // 已经输出了数组
                    format.reduceLevel();
                    format.append(format.getNewline()).append(format.getCurrentIndent());
                    format.append("]");
                }
                else {
                    node$i.value2Json(node$i.nodeValue, format);
                }
            }
            format.reduceLevel();
            format.append(format.getNewline()).append(format.getCurrentIndent());
            format.append("}");
        }
    }

    public static JingNode parseJson(String jsonContent) throws JingException {
        CharReader charReader = new CharReader(new StringReader(jsonContent));
        TokenList tokens = new Tokenizer().tokenize(charReader);
        Token token = tokens.next();
        if (null == token) {
            return new JingNode();
        } else if (token.getTokenType() == TokenType.BEGIN_OBJECT) {
            return parseJson(tokens, null);
        }
        else {
            throw new JingException("invalid begin token");
        }
    }

    public static JingNode parseJson(TokenList tokens, String rootName) throws JingException {
        int expectToken = TokenType.STRING.getTokenCode() | TokenType.END_OBJECT.getTokenCode();
        String key = null;
        Object value;
        rootName = StringUtil.ifEmpty(rootName, Const.CARRIER_ROOT_NODE);
        JingNode node = new JingNode(rootName);
        while (tokens.hasMore()) {
            Token token = tokens.next();
            TokenType tokenType = token.getTokenType();
            String tokenValue = token.getValue();
            switch (tokenType) {
                case BEGIN_OBJECT:
                    tokenType.checkExpectToken(expectToken);
                    node.addNode(parseJson(tokens, key));
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_OBJECT.getTokenCode();
                    break;
                case END_OBJECT:
                    tokenType.checkExpectToken(expectToken);
                    return node;
                case BEGIN_ARRAY:
                    tokenType.checkExpectToken(expectToken);
                    node.parseJsonArr(tokens, key);
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_OBJECT.getTokenCode();
                    break;
                case NULL:
                    tokenType.checkExpectToken(expectToken);
                    node.setValueByName(key, null);
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_OBJECT.getTokenCode();
                    break;
                case NUMBER:
                    tokenType.checkExpectToken(expectToken);
                    if (tokenValue.contains(".") || tokenValue.contains("e") || tokenValue.contains("E")) {
                        node.setValueByName(key, Double.valueOf(tokenValue));
                    }
                    else {
                        Long num = Long.valueOf(tokenValue);
                        if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
                            node.setValueByName(key, num);
                        } else {
                            node.setValueByName(key, num.intValue());
                        }
                    }
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_OBJECT.getTokenCode();
                    break;
                case BOOLEAN:
                    tokenType.checkExpectToken(expectToken);
                    node.setValueByName(key, Boolean.valueOf(token.getValue()));
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_OBJECT.getTokenCode();
                    break;
                case STRING:
                    tokenType.checkExpectToken(expectToken);
                    Token preToken = tokens.peekPrevious();
                    if (preToken.getTokenType() == TokenType.SEP_COLON) {
                        value = token.getValue();
                        node.addValueByName(key, value);
                        expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_OBJECT.getTokenCode();
                    } else {
                        key = token.getValue();
                        expectToken = TokenType.SEP_COLON.getTokenCode();
                    }
                    break;
                case SEP_COLON:
                    tokenType.checkExpectToken(expectToken);
                    expectToken = TokenType.NULL.getTokenCode()
                        | TokenType.NUMBER.getTokenCode()
                        | TokenType.BOOLEAN.getTokenCode()
                        | TokenType.STRING.getTokenCode()
                        | TokenType.BEGIN_OBJECT.getTokenCode()
                        | TokenType.BEGIN_ARRAY.getTokenCode();
                    break;
                case SEP_COMMA:
                    tokenType.checkExpectToken(expectToken);
                    expectToken = TokenType.STRING.getTokenCode();
                    break;
                case END_DOCUMENT:
                    tokenType.checkExpectToken(expectToken);
                    return node;
                default:
                    throw new JingException("unexpected token: {}", tokenType);
            }
        }
        throw new JingException("parse error, invalid tokens");
    }

    private void parseJsonArr(TokenList tokens, String rootName) throws JingException {
        int expectToken = TokenType.BEGIN_ARRAY.getTokenCode()
            | TokenType.END_ARRAY.getTokenCode()
            | TokenType.BEGIN_OBJECT.getTokenCode()
            | TokenType.NULL.getTokenCode()
            | TokenType.NUMBER.getTokenCode()
            | TokenType.BOOLEAN.getTokenCode()
            | TokenType.STRING.getTokenCode();
        rootName = StringUtil.ifEmpty(rootName, Const.CARRIER_ROOT_NODE);
        while (tokens.hasMore()) {
            Token token = tokens.next();
            TokenType tokenType = token.getTokenType();
            String tokenValue = token.getValue();
            switch (tokenType) {
                case BEGIN_OBJECT:
                    tokenType.checkExpectToken(expectToken);
                    this.addNode(parseJson(tokens, rootName));
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_ARRAY.getTokenCode();
                    break;
                case BEGIN_ARRAY:
                    tokenType.checkExpectToken(expectToken);
                    this.parseJsonArr(tokens, rootName);
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_ARRAY.getTokenCode();
                    break;
                case END_ARRAY:
                    tokenType.checkExpectToken(expectToken);
                    return;
                    // return node;
                case NULL:
                    tokenType.checkExpectToken(expectToken);
                    this.addValueByName(rootName, null);
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_ARRAY.getTokenCode();
                    break;
                case NUMBER:
                    tokenType.checkExpectToken(expectToken);
                    if (tokenValue.contains(".") || tokenValue.contains("e") || tokenValue.contains("E")) {
                        this.addValueByName(rootName, Double.valueOf(tokenValue));
                    } else {
                        Long num = Long.valueOf(tokenValue);
                        if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
                            this.addValueByName(rootName, num);
                        } else {
                            this.addValueByName(rootName, num.intValue());
                        }
                    }
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_ARRAY.getTokenCode();
                    break;
                case BOOLEAN:
                    tokenType.checkExpectToken(expectToken);
                    this.addValueByName(rootName, Boolean.valueOf(tokenValue));
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_ARRAY.getTokenCode();
                    break;
                case STRING:
                    tokenType.checkExpectToken(expectToken);
                    this.addValueByName(rootName, tokenValue);
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_ARRAY.getTokenCode();
                    break;
                case SEP_COMMA:
                    tokenType.checkExpectToken(expectToken);
                    expectToken = TokenType.STRING.getTokenCode()
                        | TokenType.NULL.getTokenCode()
                        | TokenType.NUMBER.getTokenCode()
                        | TokenType.BOOLEAN.getTokenCode()
                        | TokenType.BEGIN_ARRAY.getTokenCode()
                        | TokenType.BEGIN_OBJECT.getTokenCode();
                    break;
                case END_DOCUMENT:
                    tokenType.checkExpectToken(expectToken);
                    return;
                    // return node;
                default:
                    throw new JingException("unexpected token: {}", tokenType);
            }
        }
        throw new JingException("parse error, invalid tokens");
    }

    private void validateAttr(Map.Entry<String, String> attr) throws JingException {
        if (!attr.getKey().matches("\\w+")) {
            throw new JingException("invalid attribute: {}=\"{}\"", attr.getKey(), attr.getValue());
        }
    }
}
