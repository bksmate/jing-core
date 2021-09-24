package org.jing.core.lang;

import org.jing.core.format.Carrier2XML;
import org.jing.core.json.CharReader;
import org.jing.core.json.Token;
import org.jing.core.json.TokenList;
import org.jing.core.json.TokenType;
import org.jing.core.json.Tokenizer;
import org.jing.core.format.Carrier2Json;
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
@SuppressWarnings({ "unused", "WeakerAccess", "UnusedReturnValue", "Duplicates" })
public class Carrier {
    private volatile String name = "";

    private volatile List<Carrier> childList = new ArrayList<>();

    private volatile Object value = null;

    private LinkedHashMap<String, String> attrMap = null;

    public Carrier() {
        name = Const.CARRIER_ROOT_NODE;
    }

    public Carrier(String name) {
        this.setName(name);
        this.setValue(null);
    }

    public Carrier(String name, Object value) {
        this.setName(name);
        this.setValue(value);
    }

    public Carrier(String name, Carrier node) throws JingException {
        this.setName(name);
        this.addCarrier(node);
    }

    public Carrier setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public Carrier setValue(Object value) {
        this.value = value;
        return this;
    }

    public Object getValue() {
        return this.value;
    }

    public String getString() {
        return null == value ? "" : StringUtil.parseString(this.value);
    }

    public Carrier addCarrier(Carrier child) throws JingException {
        if (null == child) {
            throw new JingException("cannot add null carrier into child list");
        }
        this.childList.add(child);
        return this;
    }

    public Carrier addAll(Carrier carrier) {
        this.childList.addAll(carrier.childList);
        return this;
    }

    public Carrier removeChildByName(String name) {
        Carrier child;
        int size = childList.size();
        for (int i$ = 0; i$ < size; i$++) {
            child = childList.get(i$);
            if (child.name.equals(name)) {
                childList.remove(i$);
                i$ --;
                size --;
            }
        }
        return this;
    }

    public Carrier removeChildByName(String name, int seq) throws JingException {
        if (seq < 0) {
            throw new JingException("invalid seq: {}", seq);
        }
        int count = -1;
        Carrier child;
        int size = childList.size();
        for (int i$ = 0; i$ < size; i$++) {
            child = childList.get(i$);
            if (child.name.equals(name)) {
                count ++;
            }
            if (count == seq) {
                childList.remove(i$);
                break;
            }
        }
        return this;
    }

    public Carrier addValueByName(String name, Object value) {
        Carrier child = new Carrier();
        child.name = name;
        child.value = value;
        childList.add(child);
        return this;
    }

    public Carrier setValueByName(int seq, String name, Object value) throws JingException {
        if (seq < 0) {
            throw new JingException("invalid seq: {}", seq);
        }
        int count = -1;
        for (Carrier child : childList) {
            if (null == child) {
                continue;
            }
            if (child.name.equals(name)) {
                count++;
            }
            if (count == seq) {
                child.setValue(value);
                return this;
            }
        }
        return addValueByName(name, value);
    }

    public Carrier setValueByName(String name, Object value) throws JingException {
        return setValueByName(0, name, value);
    }

    public Carrier setValueByPath(String path, Object value) throws JingException {
        String[] names = path.split("\\.");
        Carrier p = this;
        int index = 0;
        String name;
        boolean flag;
        while (index < names.length) {
            name = names[index];
            flag = false;
            for (Carrier childNode : p.childList) {
                if (name.equals(childNode.name)) {
                    index ++;
                    p = childNode;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                Carrier newNode = new Carrier(name);
                p.addCarrier(newNode);
                p = newNode;
                index ++;
            }
            if (names.length == index) {
                p.value = value;
            }
        }
        return this;
    }

    public Carrier attr(String name, String value) {
        if (null == attrMap) {
            attrMap = new LinkedHashMap<>();
        }
        this.attrMap.put(name, value);
        return this;
    }

    public String attr(String name) {
        return this.attrMap.get(name);
    }

    public List<Carrier> getChildList() {
        return childList;
    }

    public List<Pair2<String, Object>> getValueChildList() {
        return getValueChildList("");
    }

    private List<Pair2<String, Object>> getValueChildList(String prefix) {
        List<Pair2<String, Object>> retList = new ArrayList<>();
        for (Carrier child : childList) {
            if (child.childList.isEmpty()) {
                retList.add(new Pair2<>(prefix + child.getName(), child.getValue()));
            }
            else {
                retList.addAll(child.getValueChildList(prefix + child.getName() + "."));
            }
        }
        return retList;
    }

    public List<Carrier> getList(String name) {
        ArrayList<Carrier> retList = null;
        for (Carrier child : childList) {
            if (child.name.equals(name)) {
                if (null == retList) {
                    retList = new ArrayList<>();
                }
                retList.add(child);
            }
        }
        return retList;
    }

    public Carrier getCarrier(String name, int seq) {
        int count = -1;
        for (Carrier child : childList) {
            if (child.name.equals(name)) {
                count ++;
            }
            if (count == seq) {
                return child;
            }
        }
        return null;
    }

    public Carrier getCarrier(String name) {
        return getCarrier(name, 0);
    }

    public int getCount(String name) {
        int count = 0;
        for (Carrier child : childList) {
            if (child.name.equals(name)) {
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
        for (Carrier child : childList) {
            if (null == child) {
                continue;
            }
            if (child.name.equals(name)) {
                count++;
            }
            if (count == seq) {
                retValue =  child.getValue();
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
        Carrier p = this;
        int index = 0;
        String name;
        boolean flag;
        while (index < names.length) {
            name = names[index];
            flag = false;
            for (Carrier childNode : p.childList) {
                if (name.equals(childNode.name)) {
                    index ++;
                    p = childNode;
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
        return p.value;
    }

    public Object getValueByPath(String path) {
        return getValueByPath(path, null);
    }

    public String getStringByPath(String path, String defaultString) {
        String[] names = path.split("\\.");
        Carrier p = this;
        int index = 0;
        String name;
        boolean flag;
        while (index < names.length) {
            name = names[index];
            flag = false;
            for (Carrier childNode : p.childList) {
                if (name.equals(childNode.name)) {
                    index ++;
                    p = childNode;
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
        return StringUtil.parseString(p.value);
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
        return asXML(new Carrier2XML());
    }

    public String asXML(Carrier2XML format) throws JingException {
        StringBuilder stbr = new StringBuilder();
        if (0 == format.getLevel()) {
            stbr.append(format.getHead());
        }
        else {
            stbr.append(format.getCurrentIndent());
        }
        stbr.append("<").append(name);
        if (null != attrMap && format.isNeedNodeAttr())  {
            for (Map.Entry<String, String> attr : attrMap.entrySet()) {
                validateAttr(attr);
                stbr.append(String.format(" %s=\"%s\"", attr.getKey(), StringUtil.ifEmpty(attr.getValue())));
            }
        }
        String content = getString();
        if (childList.isEmpty() && StringUtil.isEmpty(content)) {
            if (format.isExpandEmptyNode()) {
                stbr.append("></").append(name).append(">");
            }
            else {
                stbr.append("/>");
            }
        }
        else if (!childList.isEmpty()) {
            stbr.append(">");
            format.addLevel();
            for (Carrier child : childList) {
                if (null == child) {
                    continue;
                }
                stbr.append(format.getNewLine()).append(child.asXML(format));
            }
            format.reduceLevel();
            stbr.append(format.getNewLine()).append(format.getCurrentIndent()).append("</").append(name).append(">");
        }
        else {
            stbr.append(">").append(StringUtil.escape4XML(content)).append("</").append(name).append(">");
        }
        return stbr.toString();
    }

    public static Carrier parseXML(String xmlContent) throws JingException {
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

    public static Carrier parseXML(Node node) throws JingException {
        Carrier carrier = new Carrier(node.getNodeName());
        carrier.parseXML(node, 0);
        return carrier;
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
                    Carrier childC;
                    Node childNode;
                    for (int i$ = 0; i$ < length; i$++) {
                        childNode = nodeList.item(i$);
                        if (childNode.getNodeType() == Node.TEXT_NODE) {
                            if (this.parseXML(childNode, level + 1)) {
                                return true;
                            }
                        }
                        else {
                            childC = new Carrier(childNode.getNodeName());
                            if (childC.parseXML(childNode, level + 1)) {
                                this.addCarrier(childC);
                            }
                        }
                    }
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
        return asJson(new Carrier2Json());
    }

    public String asJson(Carrier2Json format) {
        format.clearStringBuilder();
        if (format.isNeedRootNode()) {
            format.append("{");
            key2Json(name, format);
            value2Json(this, format);
            format.append("}");
        }
        else {
            value2Json(this, format);
        }
        return format.getStringBuilder().toString();
    }

    private void key2Json(String name, Carrier2Json format) {
        format.append(format.getNewline()).append(format.getCurrentIndent())
            .append("\"").append(Carrier2Json.escape(name)).append("\":").append(format.getSpace());
    }

    private void value2Json(Object value, Carrier2Json format) {
        if (childList.isEmpty()) {
            if (null == this.value) {
                format.append("null");
            }
            else if (this.value instanceof Integer) {
                format.append(String.valueOf(this.value));
            }
            else if (this.value instanceof Long) {
                format.append(this.value + "L");
            }
            else if (this.value instanceof Float) {
                format.append(this.value + "F");
            }
            else if (this.value instanceof Double) {
                format.append(this.value + "D");
            }
            else if (this.value instanceof Boolean) {
                format.append(String.valueOf(this.value));
            }
            else {
                format.append("\"").append(StringUtil.parseString(this.value)).append("\"");
            }
        }
        else {
            format.append("{");
            format.addLevel();
            int size = childList.size();
            int[] posArr = new int[size];
            Carrier child$i, child$j;
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
                child$i = childList.get(i$);
                key2Json(child$i.name, format);
                posArr[i$] = 1;
                for (int j$ = i$ + 1; j$ < size; j$++) {
                    child$j = childList.get(j$);
                    if (child$i.name.equals(child$j.name)) {
                        posArr[j$] = 1;
                        if (!flag$) {
                            flag$ = true;
                            format.append("[");
                            format.addLevel();
                            format.append(format.getNewline()).append(format.getCurrentIndent());
                            child$i.value2Json(child$i.value, format);
                        }
                        format.append(",").append(format.getSpace());
                        format.append(format.getNewline()).append(format.getCurrentIndent());
                        child$j.value2Json(child$j.value, format);
                    }
                }
                if (flag$) {
                    // 已经输出了数组
                    format.reduceLevel();
                    format.append(format.getNewline()).append(format.getCurrentIndent());
                    format.append("]");
                }
                else {
                    child$i.value2Json(child$i.value, format);
                }
            }
            format.reduceLevel();
            format.append(format.getNewline()).append(format.getCurrentIndent());
            format.append("}");
        }
    }

    public static Carrier parseJson(String jsonContent) throws JingException {
        CharReader charReader = new CharReader(new StringReader(jsonContent));
        TokenList tokens = new Tokenizer().tokenize(charReader);
        Token token = tokens.next();
        if (null == token) {
            return new Carrier();
        } else if (token.getTokenType() == TokenType.BEGIN_OBJECT) {
            return parseJson(tokens, null);
        }
        else {
            throw new JingException("invalid begin token");
        }
    }

    public static Carrier parseJson(TokenList tokens, String rootName) throws JingException {
        int expectToken = TokenType.STRING.getTokenCode() | TokenType.END_OBJECT.getTokenCode();
        String key = null;
        Object value;
        rootName = StringUtil.ifEmpty(rootName, Const.CARRIER_ROOT_NODE);
        Carrier carrier = new Carrier(rootName);
        while (tokens.hasMore()) {
            Token token = tokens.next();
            TokenType tokenType = token.getTokenType();
            String tokenValue = token.getValue();
            switch (tokenType) {
                case BEGIN_OBJECT:
                    tokenType.checkExpectToken(expectToken);
                    carrier.addCarrier(parseJson(tokens, key));
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_OBJECT.getTokenCode();
                    break;
                case END_OBJECT:
                    tokenType.checkExpectToken(expectToken);
                    return carrier;
                case BEGIN_ARRAY:
                    tokenType.checkExpectToken(expectToken);
                    carrier.parseJsonArr(tokens, key);
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_OBJECT.getTokenCode();
                    break;
                case NULL:
                    tokenType.checkExpectToken(expectToken);
                    carrier.setValueByName(key, null);
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_OBJECT.getTokenCode();
                    break;
                case NUMBER:
                    tokenType.checkExpectToken(expectToken);
                    if (tokenValue.contains(".") || tokenValue.contains("e") || tokenValue.contains("E")) {
                        carrier.setValueByName(key, Double.valueOf(tokenValue));
                    }
                    else {
                        Long num = Long.valueOf(tokenValue);
                        if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
                            carrier.setValueByName(key, num);
                        } else {
                            carrier.setValueByName(key, num.intValue());
                        }
                    }
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_OBJECT.getTokenCode();
                    break;
                case BOOLEAN:
                    tokenType.checkExpectToken(expectToken);
                    carrier.setValueByName(key, Boolean.valueOf(token.getValue()));
                    expectToken = TokenType.SEP_COMMA.getTokenCode() | TokenType.END_OBJECT.getTokenCode();
                    break;
                case STRING:
                    tokenType.checkExpectToken(expectToken);
                    Token preToken = tokens.peekPrevious();
                    if (preToken.getTokenType() == TokenType.SEP_COLON) {
                        value = token.getValue();
                        carrier.addValueByName(key, value);
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
                    return carrier;
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
                    this.addCarrier(parseJson(tokens, rootName));
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

    @Override public String toString() {
        try {
            return asXML();
        }
        catch (JingException e) {
            e.printStackTrace();
            return super.toString();
        }
    }
}
