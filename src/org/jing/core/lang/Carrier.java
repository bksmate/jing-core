package org.jing.core.lang;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jing.core.util.CarrierUtil;
import org.jing.core.util.StringUtil;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: A Special HashMap. <br>
 *
 * @author: lxd <br>
 * @createDate 2019-01-10 17:11:02 <br>
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class Carrier {
    /**
     * Description: Value Map. <br>
     */
    private HashMap<String, Object> valueMap = new HashMap<String, Object>();

    private HashMap<String, String> extendMap = new HashMap<String, String>();

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
        CarrierUtil.checkFeasibility(valueMap);
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

    public String getString(int seq, String key, String deaultString) throws JingException {
        Object retObject = getValueByKey(seq, key, deaultString);
        return StringUtil.parseString(retObject);
    }

    public String getString(int seq, String key) throws JingException {
        Object retObject = getValueByKey(seq, key);
        return StringUtil.parseString(retObject);
    }

    public String getString(String key, String deaultString) throws JingException {
        Object retObject = getValueByKey(key, deaultString);
        return StringUtil.parseString(retObject);
    }

    public String getString(String key) throws JingException {
        Object retObject = getValueByKey(key);
        return StringUtil.parseString(retObject);
    }

    public Carrier getCarrier(String key, int seq) throws JingException {
        Object temp = getValueByKey(seq, key, null);
        if (null == temp) {
            ExceptionHandler.publish("JING-CARR-0003", "Invalid key: " + key);
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
        else {
            ExceptionHandler.publish("JING-CARR-0003", "Invalid key: " + key);
        }
        return null;
    }

    public Carrier getCarrier(String key) throws JingException {
        return getCarrier(key, 0);
    }

    public HashMap<String, Object> getValueMap() {
        return valueMap;
    }

    public void setServiceCode(String serviceCode) {
        extendMap.put("SERVICE_CODE", serviceCode);
    }

    public String getServiceCode() {
        return StringUtil.ifEmpty(extendMap.get("SERVICE_CODE"));
    }

    public Carrier putAll(Map<String, Object> valueMap) {
        this.valueMap.putAll(valueMap);
        return this;
    }

    public String asXML() {
        OutputFormat xmlFormat = new OutputFormat();
        xmlFormat.setNewlines(true);
        xmlFormat.setIndent(true);
        xmlFormat.setIndent("    ");
        return asXML(xmlFormat, true);
    }

    public String asXML(OutputFormat xmlFormat, boolean needHead) {
        Document document =DocumentHelper.createDocument();
        Element rootElement = DocumentHelper.createElement(rootNodeName);
        document.setRootElement(rootElement);
        // 设置扩展属性
        for (Map.Entry<String, String> entry$ : extendMap.entrySet()) {
            rootElement.addAttribute(entry$.getKey(), entry$.getValue());
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

    @Override
    public String toString() {
        return null == valueMap ? null : valueMap.toString();
    }
}
