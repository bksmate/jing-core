package org.jing.core.logger.local;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.Const;
import org.jing.core.lang.JingException;
import org.jing.core.lang.Pair2;
import org.jing.core.logger.JingLogger;
import org.jing.core.logger.itf.JingLoggerFactoryItf;
import org.jing.core.logger.local.appender.BaseAppender;
import org.jing.core.logger.local.appender.EmptyAppender;
import org.jing.core.logger.local.help.ResourcePool;
import org.jing.core.logger.sys.SingleLogger;
import org.jing.core.util.ClassUtil;
import org.jing.core.util.FileUtil;
import org.jing.core.util.StringUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-31 <br>
 */
@SuppressWarnings("unchecked") public final class LoggerFactory implements JingLoggerFactoryItf {

    @Override public void init(Carrier params) throws JingException {
        SingleLogger.log("init Logger.LOCAL...");
        String path = FileUtil.buildPathWithHome(params.getStringByPath("path", ""));
        File configFile = new File(path);
        if (StringUtil.isNotEmpty(path) && configFile.exists() && configFile.isFile()) {
            LocalLoggerConfiguration.configC = Carrier.parseXML(FileUtil.readFile(path));
        }
        else {
            LocalLoggerConfiguration.configC = Carrier.parseXML(Const.SYSTEM_DEFAULT_LOGGER_CONFIG);
        }
        operate();
    }

    @Override public boolean isEnable() {
        return true;
    }

    @Override public JingLogger getLogger(String name) {
        return LocalLogger.getLogger(name);
    }

    @Override public JingLogger getLogger(Class clazz) {
        return LocalLogger.getLogger(clazz);
    }

    private void operate() throws JingException {
        bindGlobalSettings();

        initAppender();

        initLoggerLevels();

        bindLevelConfig();
    }

    private void bindGlobalSettings() throws JingException {
        LocalLoggerConfiguration.stdOut = !"FALSE".equalsIgnoreCase(
            LocalLoggerConfiguration.configC.getStringByName("stdOut", "TRUE"));

        LocalLoggerConfiguration.dateFormat = LocalLoggerConfiguration.configC.getStringByName("date-format", "yyyy-MM-dd HH:mm:ss.SSS");

        LocalLoggerConfiguration.format = LocalLoggerConfiguration.configC.getStringByName("format", "[%d][%t][%N->>-%M][%p] - %m%n");

        LocalLoggerConfiguration.encoding = LocalLoggerConfiguration.configC.getStringByName("encoding", "UTF-8");
    }

    private void initAppender() throws JingException {
        int size = LocalLoggerConfiguration.configC.getCount("appender");
        Carrier appenderC, paramC;
        String name, impl;
        BaseAppender appender;
        for (int i$ = 0; i$ < size; i$++) {
            appenderC = LocalLoggerConfiguration.configC.getCarrier("appender", i$);
            paramC = appenderC.getCount("param") > 0 ? appenderC.getCarrier("param") : null;
            name = appenderC.getStringByName("name", "");
            if (null != ResourcePool.getInstance().getAppenderByName(name)) {
                SingleLogger.err("Duplicate appender: {}", name);
                continue;
            }
            impl = appenderC.getStringByName("impl", "");
            if (StringUtil.isEmpty(impl)) {
                SingleLogger.err("Empty appender impl: {}", name);
                continue;
            }
            try {
                Class clazz = ClassUtil.loadClass(impl);
                if (clazz == EmptyAppender.class) {
                    appender = LocalLoggerConfiguration.getEmptyAppender();
                }
                else {
                    try {
                        appender = (BaseAppender) ClassUtil.createInstance(clazz, new Pair2<Class<?>, Object>(Carrier.class, paramC));
                    }
                    catch (Exception e) {
                        SingleLogger.err("Failed to create appender: {}: {}", name, impl);
                        continue;
                    }
                }
                ResourcePool.getInstance().addAppender(name, appender);
            }
            catch (Exception e) {
                SingleLogger.err("Failed to create appender: {}: {}", name, impl);
            }
        }
    }

    private void initLoggerLevels() throws JingException {
        String rootLevel = LocalLoggerConfiguration.configC.getStringByName("root-level", "ALL").toUpperCase();
        String stdOutLevel = LocalLoggerConfiguration.configC.getStringByName("stdout-level", "ALL").toUpperCase();
        int size = LocalLoggerConfiguration.configC.getCount("impl");
        String impl;
        Class levelClass;
        ArrayList<Field> allFields = ClassUtil.getDeclaredFieldsByType(LocalLoggerLevel.class, LocalLoggerLevel.class);
        HashSet<Field> fieldSet = new HashSet<>(allFields);
        for (int i$ = 0; i$ < size; i$++) {
            impl = LocalLoggerConfiguration.configC.getStringByName(i$, "impl");
            levelClass = ClassUtil.loadClass(impl);
            if (ClassUtil.checkRelations(LocalLoggerLevel.class, levelClass)) {
                fieldSet.addAll(ClassUtil.getDeclaredFieldsByType(levelClass, LocalLoggerLevel.class));
            }
        }
        LocalLoggerConfiguration.levelList = new ArrayList<>();
        try {
            LocalLoggerLevel level;
            for (Field field : fieldSet) {
                LocalLoggerConfiguration.levelList.add(level = (LocalLoggerLevel) field.get(null));
                if (level.name.equals(rootLevel)) {
                    LocalLoggerConfiguration.rootLevel = level;
                }
                if (level.name.equals(stdOutLevel)) {
                    LocalLoggerConfiguration.stdOutLevel = level;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Collections.sort(LocalLoggerConfiguration.levelList, new Comparator<LocalLoggerLevel>() {
            @Override public int compare(LocalLoggerLevel o1, LocalLoggerLevel o2) {
                if (o1.priority == Integer.MIN_VALUE || o2.priority == Integer.MAX_VALUE) {
                    return -1;
                }
                if (o1.priority == Integer.MAX_VALUE || o2.priority == Integer.MIN_VALUE) {
                    return 1;
                }
                return o1.priority - o2.priority;
            }
        });
        if (null == LocalLoggerConfiguration.rootLevel) {
            LocalLoggerConfiguration.rootLevel = LocalLoggerLevel.ALL;
        }
        if (null == LocalLoggerConfiguration.stdOutLevel) {
            LocalLoggerConfiguration.stdOutLevel = LocalLoggerLevel.ALL;
        }
    }

    private void bindLevelConfig() throws JingException {
        int size = LocalLoggerConfiguration.configC.getCount("logger"), size$;
        Carrier loggerC;
        String name, appenderName;
        LocalLoggerLevel.LevelConfig config;
        HashMap<String, LocalLoggerLevel.LevelConfig> configMap = new HashMap<>();
        boolean hasAppender;
        BaseAppender appender;
        for (int i$ = 0; i$ < size; i$++) {
            loggerC = LocalLoggerConfiguration.configC.getCarrier("logger", i$);
            name = loggerC.getStringByName("name", "").toUpperCase();
            config = new LocalLoggerLevel.LevelConfig();
            config.format = loggerC.getStringByName("format", LocalLoggerConfiguration.format);
            config.encoding = loggerC.getStringByName("encoding", LocalLoggerConfiguration.encoding);
            config.dateFormat = loggerC.getStringByName("date-format", LocalLoggerConfiguration.dateFormat);
            // appender
            size$ = loggerC.getCount("appender");
            hasAppender = false;
            for (int j$ = 0; j$ < size$; j$++) {
                appenderName = loggerC.getStringByName(j$, "appender", "");
                appender = ResourcePool.getInstance().getAppenderByName(appenderName);
                if (null != appender) {
                    hasAppender = true;
                    config.appenderSet.add(appender);
                }
                else {
                    SingleLogger.err("No appender match: {}", appenderName);
                }
            }
            if (!hasAppender) {
                config.appenderSet.add(LocalLoggerConfiguration.getEmptyAppender());
            }
            configMap.put(name, config);
        }
        for (LocalLoggerLevel level : LocalLoggerConfiguration.levelList) {
            config = configMap.get(level.name);
            level.setLevelConfig(config);
        }
    }
}
