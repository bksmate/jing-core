package org.jing.core.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jing.core.json.CharReader;
import org.jing.core.json.Parser;
import org.jing.core.json.TokenList;
import org.jing.core.json.Tokenizer;
import org.jing.core.lang.Carrier;
import org.jing.core.lang.ExceptionHandler;
import org.jing.core.lang.JingException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CarrierUtil {
    public static void checkFeasibility(HashMap<String, Object> valueMap) throws JingException {
        // 检测valueMap是不是为空
        GenericUtil.throwNullException(valueMap, "JING-CARR-0001", "Value Map Is Null");
    }

    public static Carrier string2Carrier(String content) throws JingException {
        return string2Carrier(content, false);
    }

    public static Carrier string2Carrier(String content, boolean ignoreRootNodeFormat) throws JingException {
        Document document = ToUtil.string2xml(content);
        return ToUtil.document2Carrier(document);
    }

    public static  int getCount(HashMap<String, Object> valueMap, String key) throws JingException {
        checkFeasibility(valueMap);
        if (valueMap.containsKey(key)) {
            Object object = valueMap.get(key);
            if (object instanceof List) {
                return GenericUtil.countList((List<?>) object);
            }
            else {
                return 1;
            }
        }
        else {
            return 0;
        }
    }

    public static void addValueByKey(HashMap<String, Object> hashMap, String key, Object value) throws JingException {
        checkFeasibility(hashMap);
        if (value instanceof Carrier) {
            value = ((Carrier) value).getValueMap();
        }
        if (hashMap.containsKey(key)) {
            Object node = hashMap.get(key);
            if (node instanceof List) {
                ((List<Object>) node).add(value);
            }
            else {
                List<Object> _list = new ArrayList<Object>();
                hashMap.put(key, _list);
                _list.add(node);
                _list.add(value);
            }
        }
        else {
            hashMap.put(key, value);
        }
    }

    public static void setValueByKey(HashMap<String, Object> hashMap, String key, Object value, int seq) throws JingException {
        checkFeasibility(hashMap);
        if (value instanceof Carrier) {
            value = ((Carrier) value).getValueMap();
        }
        if (hashMap.containsKey(key)) {
            Object node = hashMap.get(key);
            if (node instanceof List) {
                int size = GenericUtil.countList((List<Object>) node);
                if (seq + 1 > size) {
                    ((List<Object>) node).add(value);
                }
                else {
                    ((List<Object>) node).set(seq, value);
                }
            }
            else if (0 == seq) {
                hashMap.put(key, value);
            }
            else {
                List<Object> _list = new ArrayList<Object>();
                hashMap.put(key, _list);
                _list.add(node);
                _list.add(value);
            }
        }
        else {
            hashMap.put(key, value);
        }
    }

    public static Object getValueByKey(HashMap<String, Object> hashMap, String key, Object defaultValue, int seq) throws JingException {
        checkFeasibility(hashMap);
        if (!hashMap.containsKey(key)) {
            return defaultValue;
        }
        else {
            Object value = hashMap.get(key);
            if (null == value) {
                return null;
            }
            else {
                if (value instanceof List) {
                    int count = GenericUtil.countList((List<?>) value);
                    if (seq < count) {
                        return ((List<?>) value).get(seq);
                    }
                    else {
                        return ((List<?>) value).get(count - 1);
                    }
                }
                else {
                    return value;
                }
            }
        }
    }

    public static void object2Element(Object object, String name, Element element) {
        /*if (null == object) {
            Element retElement = DocumentHelper.createElement(name);
            element.add(retElement);
        }
        else */
        if (object instanceof List) {
            int size = ((List<Object>) object).size();
            for (int i = 0; i < size; i++) {
                Element retElement = DocumentHelper.createElement(name);
                element.getParent().add(retElement);
                object2Element(((List<Object>) object).get(i), name, retElement);
            }
            if (size > 1) {
                element.getParent().remove(element);
            }
        }
        else if (object instanceof HashMap) {
            for (Entry<String, Object> entry: ((HashMap<String, Object>) object).entrySet()) {
                Element retElement = DocumentHelper.createElement(entry.getKey());
                element.add(retElement);
                object2Element(entry.getValue(), entry.getKey(), retElement);
            }
        }
        else {
            element.setText(String.valueOf(object));
        }
    }

    public static Element generateTextElement(String name, String value) {
        Element retElement = DocumentHelper.createElement(name);
        retElement.setText(value);
        return retElement;
    }

    public static String carrier2JsonContent(Carrier carrier) {
        return carrier2JsonContent(carrier, false);
    }

    public static String carrier2JsonContent(Carrier carrier, boolean needRootNode) {
        if (needRootNode) {
            StringBuilder stbr = new StringBuilder("{");
            carrierKey2JsonContent(stbr, carrier.getRootNodeName())
                .append(carrierValue2JsonContent(carrier.getValueMap())).append("}");
            return stbr.toString();
        }
        else {
            return carrierValue2JsonContent(carrier.getValueMap());
        }
    }

    private static StringBuilder carrierKey2JsonContent(StringBuilder stbr, Object keyNode) {
        stbr.append("\"").append(StringUtil.parseString(keyNode)).append("\":");
        return stbr;
    }

    private static String carrierValue2JsonContent(Object valueNode) {
        StringBuilder stbr = new StringBuilder("");
        if (null == valueNode) {
            stbr.append("null");
        }
        else if (valueNode instanceof List) {
            stbr.append("[");
            int count = GenericUtil.countList((List<?>) valueNode);
            for (int i$ = 0; i$ < count; i$++) {
                if (i$ > 0) {
                    stbr.append(",");
                }
                stbr.append(carrierValue2JsonContent(((List<?>) valueNode).get(i$)));
            }
            stbr.append("]");
        }
        else if (valueNode instanceof Map) {
            stbr.append("{");
            Iterator iterator = ((Map) valueNode).keySet().iterator();
            Object key;
            Object value;
            boolean f$ = false;
            while (iterator.hasNext()) {
                key = iterator.next();
                value = ((Map) valueNode).get(key);
                if (f$) {
                    stbr.append(",");
                }
                else {
                    f$ = true;
                }
                carrierKey2JsonContent(stbr, key).append(carrierValue2JsonContent(value));
            }
            stbr.append("}");
        }
        else if (valueNode instanceof Carrier) {
            stbr.append(carrier2JsonContent(((Carrier) valueNode)));
        }
        else if (valueNode instanceof Long
            || valueNode instanceof Integer
            || valueNode instanceof Short
            || valueNode instanceof Double
            || valueNode instanceof Float) {
            stbr.append(StringUtil.parseString(valueNode));
        }
        else {
            stbr.append("\"").append(StringUtil.parseString(valueNode)).append("\"");
        }
        return stbr.toString();
    }

    public static Carrier jsonContent2Carrier(String jsonContent) throws JingException {
        return jsonContent2Carrier(jsonContent, null);
    }

    public static Carrier jsonContent2Carrier(String jsonContent, String rootNodeName) throws JingException {
        jsonContent = jsonContent.trim();
        CharReader charReader = new CharReader(new StringReader(jsonContent));
        TokenList tokens = new Tokenizer().tokenize(charReader);
        return new Parser().parse(tokens, rootNodeName);
    }
}
