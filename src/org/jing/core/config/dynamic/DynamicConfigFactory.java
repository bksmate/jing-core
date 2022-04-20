package org.jing.core.config.dynamic;

import org.jing.core.config.ConfigProperty;
import org.jing.core.config.JDynamicConfig;
import org.jing.core.config.statics.StaticConfigFactory;
import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.logger.JingLogger;
import org.jing.core.util.FileUtil;
import org.jing.core.util.GenericUtil;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-22 <br>
 */
@SuppressWarnings("Duplicates") public class DynamicConfigFactory {
    private static final JingLogger LOGGER = JingLogger.getLogger(DynamicConfigFactory.class);

    private static ConcurrentHashMap<Class<? extends JDynamicConfig>, String> configMap ;

    private static ConcurrentHashMap<Class<? extends JDynamicConfig>, String> getConfigMap() {
        if (null == configMap) {
            synchronized (StaticConfigFactory.class) {
                configMap = new ConcurrentHashMap<>();
            }
        }
        return configMap;
    }

    public static <T> T createDynamicConfig(Class<T> clazz) throws JingException {
        if (!JDynamicConfig.class.isAssignableFrom(clazz)) {
            throw new JingException("class must implement from JDynamicConfig");
        }
        LOGGER.debug("create dynamic config: {}", clazz.getName());
        String filePath = getConfigMap().get(clazz);
        Carrier configC;
        try {
            configC = Carrier.parseXML(FileUtil.readFile(filePath));
        }
        catch (JingException e) {
            LOGGER.error(e);
            throw new JingException("failed to parse dynamic config: {}", filePath);
        }
        try {
            T instance = clazz.newInstance();
            ((JDynamicConfig) instance).operate(configC);
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
                        LOGGER.debug("read config by [path: {}], set [default: {}]", path, defaultValue);
                    }
                    else {
                        LOGGER.debug("read config by [path: {}], set [value: {}]", path, value);
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

    public static synchronized void registerDynamicConfig(Class<? extends JDynamicConfig> clazz, String filePath) {
        getConfigMap().put(clazz, filePath);
    }
}
