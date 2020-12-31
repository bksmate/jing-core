package org.jing.core.logger;

import org.apache.log4j.Level;
import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.lang.itf.JInit;
import org.jing.core.util.CarrierUtil;
import org.jing.core.util.ClassUtil;
import org.jing.core.util.FileUtil;

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
public class JingLoggerInit implements JInit {
    private static Carrier parameter;

    @Override public void init(Carrier params) throws JingException {
        parameter = params;
        String path = FileUtil.buildPathWithHome(parameter.getStringByPath("path", ""));
        JingLoggerConfiguration.configC = CarrierUtil.string2Carrier(FileUtil.readFile(path));

        operate();
    }

    private void operate() throws JingException {
        initLoggerLevels();

        bindLevelConfig();

        System.out.println(JingLoggerConfiguration.levelList);
    }

    private void initLoggerLevels() throws JingException {
        String rootLevel = JingLoggerConfiguration.configC.getString("root-level", "ALL").toUpperCase();
        int size = JingLoggerConfiguration.configC.getCount("impl");
        String impl;
        Class levelClass;
        Field[] origLevels = Level.class.getDeclaredFields();
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
        String name, filePath;
        JingLoggerLevel.LevelConfig config;
        HashMap<String, JingLoggerLevel.LevelConfig> configMap = new HashMap<>();
        for (int i$ = 0; i$ < size; i$++) {
            loggerC = JingLoggerConfiguration.configC.getCarrier("logger", i$);
            name = loggerC.getString("name", "").toUpperCase();
            size$ = loggerC.getCount("file");
            config = new JingLoggerLevel.LevelConfig();
            for (int j$ = 0; j$ < size$; j$++) {
                filePath = loggerC.getString(j$, "file");
                config.loggerPathSet.add(filePath);
            }
            config.format = loggerC.getString("format", "%m%n");
            config.encoding = loggerC.getString("encoding", "utf-8");
            configMap.put(name, config);
        }
        for (JingLoggerLevel level : JingLoggerConfiguration.levelList) {
            config = configMap.get(level.name);
            level.setLevelConfig(config);
        }
    }
}
