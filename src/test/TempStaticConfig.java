package test;

import org.jing.core.config.ConfigProperty;
import org.jing.core.config.JStaticConfig;
import org.jing.core.lang.BaseDto;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-25 <br>
 */
public class TempStaticConfig extends BaseDto implements JStaticConfig {
    private String path;

    @ConfigProperty(path = "init.parameters.path", required = true)
    public TempStaticConfig setPath(String path) {
        this.path = path;
        return this;
    }

    public String getPath() {
        return path;
    }
}
