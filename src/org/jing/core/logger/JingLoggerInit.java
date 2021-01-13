package org.jing.core.logger;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.Const;
import org.jing.core.lang.JingException;
import org.jing.core.lang.Pair2;
import org.jing.core.lang.itf.JInit;
import org.jing.core.logger.appender.EmptyAppender;
import org.jing.core.logger.appender.BaseAppender;
import org.jing.core.logger.help.ResourcePool;
import org.jing.core.util.CarrierUtil;
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
public final class JingLoggerInit implements JInit {

    @Override public void init(Carrier params) throws JingException {
        String path = FileUtil.buildPathWithHome(params.getStringByPath("path", ""));
        File configFile = new File(path);
        if (StringUtil.isNotEmpty(path) && configFile.exists() && configFile.isFile()) {
            JingLoggerConfiguration.configC = CarrierUtil.string2Carrier(FileUtil.readFile(path));
        }
        else {
            JingLoggerConfiguration.configC = CarrierUtil.string2Carrier(Const.SYSTEM_DEFAULT_LOGGER_CONFIG);
        }
        operate();
    }

    private void operate() throws JingException {
        bindGlobalSettings();

        initAppender();

        initLoggerLevels();

        bindLevelConfig();
    }

    private void bindGlobalSettings() throws JingException {
        JingLoggerConfiguration.stdOut = !"FALSE".equalsIgnoreCase(JingLoggerConfiguration.configC.getString("stdOut", "TRUE"));

        JingLoggerConfiguration.dateFormat = JingLoggerConfiguration.configC.getString("date-format", "yyyy-MM-dd HH:mm:ss.SSS");

        JingLoggerConfiguration.format = JingLoggerConfiguration.configC.getString("format", "[%d][%t][%N->>-%M][%p] - %m%n");

        JingLoggerConfiguration.encoding = JingLoggerConfiguration.configC.getString("encoding", "UTF-8");
    }

    private void initAppender() throws JingException {
        int size = JingLoggerConfiguration.configC.getCount("appender");
        Carrier appenderC, paramC;
        String name, impl;
        BaseAppender appender;
        for (int i$ = 0; i$ < size; i$++) {
            appenderC = JingLoggerConfiguration.configC.getCarrier("appender", i$);
            paramC = appenderC.getCount("param") > 0 ? appenderC.getCarrier("param") : null;
            name = appenderC.getString("name", "");
            if (null != ResourcePool.getInstance().getAppenderByName(name)) {
                // TODO
                // 存在重名Appender
                continue;
            }
            impl = appenderC.getString("impl", "");
            if (StringUtil.isEmpty(impl)) {
                // TODO
                continue;
            }
            try {
                Class clazz = ClassUtil.loadClass(impl);
                if (clazz == EmptyAppender.class) {
                    appender = JingLoggerConfiguration.getEmptyAppender();
                }
                else {
                    try {
                        appender = (BaseAppender) ClassUtil.createInstance(clazz, new Pair2<Class<?>, Object>(Carrier.class, paramC));
                    }
                    catch (Exception e) {
                        // TODO
                        continue;
                    }
                }
                ResourcePool.getInstance().addAppender(name, appender);
            }
            catch (Exception e) {
                // TODO
                continue;
            }
        }
    }

    private void initLoggerLevels() throws JingException {
        String rootLevel = JingLoggerConfiguration.configC.getString("root-level", "ALL").toUpperCase();
        int size = JingLoggerConfiguration.configC.getCount("impl");
        String impl;
        Class levelClass;
        ArrayList<Field> allFields = ClassUtil.getDeclaredFieldsByType(JingLoggerLevel.class, JingLoggerLevel.class);
        HashSet<Field> fieldSet = new HashSet<>(allFields);
        for (int i$ = 0; i$ < size; i$++) {
            impl = JingLoggerConfiguration.configC.getString(i$, "impl");
            levelClass = ClassUtil.loadClass(impl);
            if (ClassUtil.checkRelations(JingLoggerLevel.class, levelClass)) {
                fieldSet.addAll(ClassUtil.getDeclaredFieldsByType(levelClass, JingLoggerLevel.class));
            }
        }
        JingLoggerConfiguration.levelList = new ArrayList<>();
        try {
            JingLoggerLevel level;
            for (Field field : fieldSet) {
                JingLoggerConfiguration.levelList.add(level = (JingLoggerLevel) field.get(null));
                if (level.name.equals(rootLevel)) {
                    JingLoggerConfiguration.rootLevel = level;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Collections.sort(JingLoggerConfiguration.levelList, new Comparator<JingLoggerLevel>() {
            @Override public int compare(JingLoggerLevel o1, JingLoggerLevel o2) {
                if (o1.priority == Integer.MIN_VALUE || o2.priority == Integer.MAX_VALUE) {
                    return -1;
                }
                if (o1.priority == Integer.MAX_VALUE || o2.priority == Integer.MIN_VALUE) {
                    return 1;
                }
                return o1.priority - o2.priority;
            }
        });
    }

    private void bindLevelConfig() throws JingException {
        int size = JingLoggerConfiguration.configC.getCount("logger"), size$;
        Carrier loggerC;
        String name, filePath, appenderName;
        JingLoggerLevel.LevelConfig config;
        HashMap<String, JingLoggerLevel.LevelConfig> configMap = new HashMap<>();
        File file, dir;
        boolean hasAppender;
        BaseAppender appender;
        JingLoggerConfiguration.writerMap = new HashMap<>();
        HashMap<String, JingLoggerWriter> writerMap = new HashMap<>();
        JingLoggerWriter writer;
        for (int i$ = 0; i$ < size; i$++) {
            loggerC = JingLoggerConfiguration.configC.getCarrier("logger", i$);
            name = loggerC.getString("name", "").toUpperCase();
            config = new JingLoggerLevel.LevelConfig();
            config.format = loggerC.getString("format", JingLoggerConfiguration.format);
            config.encoding = loggerC.getString("encoding", JingLoggerConfiguration.encoding);
            config.dateFormat = loggerC.getString("date-format", JingLoggerConfiguration.dateFormat);
            // file
            /*size$ = loggerC.getCount("file");
            for (int j$ = 0; j$ < size$; j$++) {
                filePath = FileUtil.buildPathWithHome(loggerC.getString(j$, "file"));
                file = new File(filePath);
                filePath = file.getAbsolutePath();
                try {
                    dir = file.getParentFile();
                    if (null != dir && !FileUtil.mkdirs(dir)) {
                        System.err.println("Failed to mkdir: " + dir.getAbsolutePath());
                    }
                }
                catch (Exception ignored) {}
                if (null == (writer = writerMap.get(filePath))) {
                    writerMap.put(filePath, (writer = new JingLoggerWriter(filePath)));
                }
                config.writerSet.add(writer);
            }*/
            // appender
            size$ = loggerC.getCount("appender");
            hasAppender = false;
            for (int j$ = 0; j$ < size$; j$++) {
                appenderName = loggerC.getString(j$, "appender", "");
                appender = ResourcePool.getInstance().getAppenderByName(appenderName);
                if (null != appender) {
                    hasAppender = true;
                    config.appenderSet.add(appender);
                }
                else {
                    // TODO
                    // 提示没找到appender
                }
            }
            if (!hasAppender) {
                config.appenderSet.add(JingLoggerConfiguration.getEmptyAppender());
            }
            configMap.put(name, config);
        }
        for (JingLoggerLevel level : JingLoggerConfiguration.levelList) {
            config = configMap.get(level.name);
            level.setLevelConfig(config);
        }
    }
}
