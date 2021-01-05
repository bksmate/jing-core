package org.jing.core.logger;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.Const;
import org.jing.core.lang.JingException;
import org.jing.core.lang.itf.JInit;
import org.jing.core.util.CarrierUtil;
import org.jing.core.util.ClassUtil;
import org.jing.core.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-31 <br>
 */
public class JingLoggerInit implements JInit {

    @Override public void init(Carrier params) throws JingException {
        String path = FileUtil.buildPathWithHome(params.getStringByPath("path", ""));
        File configFile = new File(path);
        if (configFile.exists() && configFile.isFile()) {
            JingLoggerConfiguration.configC = CarrierUtil.string2Carrier(FileUtil.readFile(path));
        }
        else {
            JingLoggerConfiguration.configC = CarrierUtil.string2Carrier(Const.SYSTEM_DEFAULT_LOGGER_CONFIG);
        }

        operate();
    }

    private void operate() throws JingException {
        bindGlobalSettings();

        initLoggerLevels();

        bindLevelConfig();

        bindWriter();
        // System.out.println(JingLoggerConfiguration.levelList);
    }

    private void bindGlobalSettings() throws JingException {
        JingLoggerConfiguration.stdout = !"FALSE".equalsIgnoreCase(JingLoggerConfiguration.configC.getString("stdout", "TRUE"));

        JingLoggerConfiguration.dateFormat = JingLoggerConfiguration.configC.getString("date-format", "yyyy-MM-dd HH:mm:ss.SSS");

        JingLoggerConfiguration.format = JingLoggerConfiguration.configC.getString("format", "[%d][%t][%N->>-%M][%p] - %m%n");

        JingLoggerConfiguration.encoding = JingLoggerConfiguration.configC.getString("encoding", "UTF-8");
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
        String name, filePath;
        JingLoggerLevel.LevelConfig config;
        HashMap<String, JingLoggerLevel.LevelConfig> configMap = new HashMap<>();
        File file, dir;
        JingLoggerConfiguration.writerMap = new HashMap<>();
        JingLoggerConfiguration.contentMap = new HashMap<>();
        for (int i$ = 0; i$ < size; i$++) {
            loggerC = JingLoggerConfiguration.configC.getCarrier("logger", i$);
            name = loggerC.getString("name", "").toUpperCase();
            size$ = loggerC.getCount("file");
            config = new JingLoggerLevel.LevelConfig();
            config.format = loggerC.getString("format", JingLoggerConfiguration.format);
            config.encoding = loggerC.getString("encoding", JingLoggerConfiguration.encoding);
            config.dateFormat = loggerC.getString("date-format", JingLoggerConfiguration.dateFormat);
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
                config.loggerPathSet.add(filePath);
                JingLoggerConfiguration.writerMap.put(filePath, null);
                JingLoggerConfiguration.contentMap.put(filePath, new ConcurrentLinkedQueue<byte[]>());
            }
            configMap.put(name, config);
        }
        for (JingLoggerLevel level : JingLoggerConfiguration.levelList) {
            config = configMap.get(level.name);
            level.setLevelConfig(config);
        }
    }

    private void bindWriter() {
        String filePath;
        for (Map.Entry<String, FileOutputStream> entry : JingLoggerConfiguration.writerMap.entrySet()) {
            filePath = entry.getKey();
            try {
                entry.setValue(new FileOutputStream(filePath, true));
            }
            catch (Exception e) {
                System.err.println("Failed to open FileOutputSteam: " + filePath);
                entry.setValue(null);
            }
        }
    }
}
