package org.jing.core.config.dynamic;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.config.ConfigProperty;
import org.jing.core.config.JDynamicConfig;
import org.jing.core.logger.JingLogger;
import org.jing.core.util.GenericUtil;
import test.TempDynamicConfig;

import java.lang.reflect.Method;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-22 <br>
 */
public class DynamicConfigFactory {
    private static final JingLogger LOGGER = JingLogger.getLogger(DynamicConfigFactory.class);

    public static <T> T createDynamicConfig(Class<T> clazz) throws JingException {
        if (!JDynamicConfig.class.isAssignableFrom(clazz)) {
            throw new JingException("class must implement from JDynamicConfig");
        }
        LOGGER.debug("create dynamic config: {}", clazz.getName());
        try {
            T instance = clazz.newInstance();
            Carrier configC = ((JDynamicConfig) instance).readConfigCarrier();
            Method[] methods = clazz.getDeclaredMethods();
            Method method;
            ConfigProperty setter;
            String value;
            String path;
            String defaultValue;
            int size = GenericUtil.countArray(methods);
            for (int i$ = 0; i$ < size; i$++) {
                method = methods[i$];
                setter = method.getAnnotation(ConfigProperty.class);
                if (null != setter) {
                    value = configC.getStringByPath((path = setter.path()));
                    if (null == value && setter.required()) {
                        throw new JingException("{} required", path);
                    }
                    if (null == value) {
                        value = (defaultValue = setter.defaultValue());
                        LOGGER.debug("read config by [path: {}], use [default: {}]", path, defaultValue);
                    }
                    else {
                        LOGGER.debug("read config by [path: {}], value [value: {}]", path, value);
                    }
                    method.invoke(instance, value);
                }
            }
            return instance;
        }
        catch (Throwable t) {
            throw new JingException(t, "failed to create instance");
        }
    }

    public static void main(String[] args) throws JingException {
        createDynamicConfig(TempDynamicConfig.class);
    }
}
