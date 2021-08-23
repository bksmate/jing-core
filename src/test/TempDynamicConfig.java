package test;

import org.jing.core.lang.BaseDto;
import org.jing.core.lang.Carrier;
import org.jing.core.config.dynamic.DynamicConfigFactory;
import org.jing.core.lang.JingException;
import org.jing.core.config.ConfigProperty;
import org.jing.core.config.JDynamicConfig;
import org.jing.core.util.FileUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-22 <br>
 */
public class TempDynamicConfig extends BaseDto implements JDynamicConfig {
    private String node = "temp";

    @ConfigProperty(path = "service.path", required = true)
    public TempDynamicConfig setNode(String node) {
        this.node = node;
        return this;
    }

    @Override public Carrier readConfigCarrier() throws JingException {
        String xmlContent = FileUtil.readFile("temp/temp$1.xml");
        return Carrier.parseXML(xmlContent);
    }

    public static void main(String[] args) throws JingException {
        TempDynamicConfig config = DynamicConfigFactory.createDynamicConfig(TempDynamicConfig.class);
        System.out.println(config);
    }
}
