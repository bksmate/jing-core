package org.jing.core.config.statics;

import org.jing.core.config.ConfigProperty;
import org.jing.core.config.JStaticConfig;
import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.logger.JingLogger;
import org.jing.core.util.GenericUtil;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-25 <br>
 */
@SuppressWarnings({ "Duplicates", "unused" })
public class StaticConfigFactory {
    private static final JingLogger LOGGER = JingLogger.getLogger(StaticConfigFactory.class);

    private static final HashMap<Class<? extends JStaticConfig>, Carrier> CONFIG_MAP = new HashMap<>();

    public static <T> T createStaticConfig(Class<T> clazz) throws JingException {
        if (!JStaticConfig.class.isAssignableFrom(clazz)) {
            throw new JingException("class must implement from JStaticConfig");
        }
        LOGGER.debug("create static config: {}", clazz.getName());
        Carrier configC = CONFIG_MAP.get(clazz);
        if (null == configC) {
            throw new JingException("static config has not registered");
        }
        try {
            T instance = clazz.newInstance();
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

    public static synchronized void registerStaticConfig(Class<? extends JStaticConfig> clazz, Carrier carrier) {
        CONFIG_MAP.put(clazz, carrier);
    }
}
