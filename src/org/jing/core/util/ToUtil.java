package org.jing.core.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;

import java.util.List;

@SuppressWarnings("WeakerAccess") public class ToUtil {
    public static Document string2xml(String content) throws JingException {
        try {
            return DocumentHelper.parseText(content);
        }
        catch (DocumentException e) {
            throw new JingException(e, "Failed To Transfer String To XML Document");
        }
    }

    public static Carrier document2Carrier(Document document) throws JingException {
        Carrier retCarrier = new Carrier();
        try {
            Element rootEle = document.getRootElement();
            // 装载扩展属性.
            retCarrier.setServiceCode(rootEle.attributeValue("SERVICE_CODE"));
            retCarrier.setRootNodeName(rootEle.getName());
            // 装载数据.
            element2Carrier(document.getRootElement(), retCarrier, true);
        }
        catch (Exception e) {
            throw new JingException(e, "Invalid XML format.");
        }

        return retCarrier;
    }

    public static void element2Carrier(Element element, Carrier parentCarrier, boolean isRootElement) throws JingException {
        if (element.isTextOnly()) {
            parentCarrier.addValueByKey(element.getName(), element.getText().trim());
        }
        else {
            Carrier newCarrier = new Carrier();
            List<Element> elements = element.elements();
            int count = GenericUtil.countList(elements);
            Element element$;
            for (int i$ = 0; i$ < count; i$++) {
                element$ = elements.get(i$);
                element2Carrier(element$, newCarrier, false);
            }
            if (!isRootElement) {
                parentCarrier.addValueByKey(element.getName(), newCarrier);
            }
            else {
                parentCarrier.setValueMap(newCarrier.getValueMap());
            }
        }
    }
}
