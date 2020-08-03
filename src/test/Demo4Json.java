package test;

import org.jing.core.lang.Carrier;
import org.jing.core.util.CarrierUtil;

import java.lang.Exception;
import java.util.LinkedHashMap;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class Demo4Json {
    private Demo4Json() throws Exception {
        LinkedHashMap map = new LinkedHashMap();
        String jsonContent = "{\"str\":\"Hello\",\"number\":123,\"empty\":null,\"jing\":{\"sub\":{\"empty\":null,\"ROW\":[1,\"2\",\"3\"]},\"ROW\":[\"1\",\"2\",\"3\"]}}";
        /*JSONParser jsonParser = new JSONParser();
        JsonObject json = (JsonObject) jsonParser.fromJSON(jsonContent);
        System.out.println(json);*/
        Carrier jsonCarrier = CarrierUtil.jsonContent2Carrier(jsonContent);
        System.out.println(jsonCarrier.asXML());
        jsonContent = CarrierUtil.carrier2JsonContent(jsonCarrier);
        System.out.println(jsonContent);
        jsonCarrier = CarrierUtil.jsonContent2Carrier(jsonContent);
        System.out.println(jsonCarrier.asXML());
        jsonContent = CarrierUtil.carrier2JsonContent(jsonCarrier);
        System.out.println(jsonContent);
        jsonCarrier = CarrierUtil.jsonContent2Carrier(jsonContent);
        System.out.println(jsonCarrier.asXML());
        jsonContent = CarrierUtil.carrier2JsonContent(jsonCarrier);
        System.out.println(jsonContent);
        jsonCarrier = CarrierUtil.jsonContent2Carrier(jsonContent);
        System.out.println(jsonCarrier.asXML());
        jsonContent = CarrierUtil.carrier2JsonContent(jsonCarrier);
        System.out.println(jsonContent);
        jsonCarrier = CarrierUtil.jsonContent2Carrier(jsonContent);
        System.out.println(jsonCarrier.asXML());
        jsonContent = CarrierUtil.carrier2JsonContent(jsonCarrier);
        System.out.println(jsonContent);
        jsonCarrier = CarrierUtil.jsonContent2Carrier(jsonContent);
        System.out.println(jsonCarrier.asXML());
        jsonContent = CarrierUtil.carrier2JsonContent(jsonCarrier);
        System.out.println(jsonContent);
    }

    public static void main(String[] args) throws Exception {
        new Demo4Json();
    }
}
