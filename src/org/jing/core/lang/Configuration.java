package org.jing.core.lang;

import org.jing.core.lang.itf.JInit;
import org.jing.core.util.CarrierUtil;
import org.jing.core.util.ClassUtil;
import org.jing.core.util.FileUtil;
import org.jing.core.util.StringUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-01-09 <br>
 */
public class Configuration {
    private static volatile Configuration ourInstance = null;

    private Carrier configCarrier;

    private static boolean initFlag = false;

    /**
     * 存储继承了JInit接口的类和对应实例的map. <br>
     */
    private static final Hashtable<Class<? extends JInit>, JInit> jInitMap = new Hashtable<Class<? extends JInit>, JInit>();

    private void loadConfigFile() {
        synchronized (Configuration.class) {
            String jingHome = System.getProperty("JING_HOME");
            if (StringUtil.isEmpty(jingHome)) {
                jingHome = FileUtil.buildPath("config?system.xml");
            }
            String configContent = readFile(jingHome);
            configContent = StringUtil.preOperation4XML(configContent);
            try {
                configCarrier = CarrierUtil.string2Carrier(configContent);
            }
            catch (Exception e) {
                log("Failed to transfer configuration xml.");
                log(StringUtil.getErrorStack(e));
                System.exit(-1);
            }
        }
    }

    public void reloadConfigFile() {
        synchronized (Configuration.class) {
            loadConfigFile();
        }
    }

    public String getConfig(String path) throws JingException {
        synchronized (Configuration.class) {
            return configCarrier.getStringByPath(path);
        }
    }

    public String getConfig(String path, String defaultString) throws JingException {
        synchronized (Configuration.class) {
            return StringUtil.ifEmpty(configCarrier.getStringByPath(path), defaultString);
        }
    }

    public Carrier getConfigCarrier(String path) throws JingException {
        synchronized (Configuration.class) {
            return configCarrier.getCarrier(path);
        }
    }

    public Carrier getConfigCarrier(String path, int seq) throws JingException {
        synchronized (Configuration.class) {
            return configCarrier.getCarrier(path, seq);
        }
    }

    public static Configuration getInstance() {
        if (null == ourInstance) {
            synchronized (Configuration.class) {
                if (null == ourInstance) {
                    ourInstance = new Configuration();
                    // 初始化
                    ourInstance.init();
                    initFlag = true;
                }
            }
        }

        return ourInstance;
    }

    private Configuration() {
        loadConfigFile();
    }

    private String readFile(String filePath) {
        StringBuilder stbr = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "utf-8"));
            String row;
            boolean newLineFlag = false;
            while (null != (row = br.readLine())) {
                if (newLineFlag) {
                    stbr.append("\r\n");
                }
                else {
                    newLineFlag = true;
                }
                stbr.append(row);
            }
            br.close();
        }
        catch (Exception e) {
            log("Failed to read file: {}", filePath);
            log(StringUtil.getErrorStack(e));
        }

        return stbr.toString();
    }

    private void writeFile(String filePath, String content) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath), true), "utf-8"));
            bw.write(content);
            bw.newLine();
            bw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void log(String content, String... parameters) {
        content = StringUtil.mixParameters(content, parameters);
        content = getLogPrefix() + content;
        System.out.println(content);
        File logFile = new File("log");
        if (logFile.exists() || logFile.mkdirs()) {
            writeFile(FileUtil.buildPath("log?start.log"), content);
        }
        else {
            System.out.println("Failed to create start.log, shut down.");
        }
    }

    private String getLogPrefix() {
        return StringUtil.getDate("===[yyyy-MM-dd HH:mm:ss.SSS]===: ");
    }

    private void init() {
        // 初始化某些需要预处理的配置.
        try {
            // 注册初始化类
            register();
        }
        catch (Exception e) {
            log("Failed to initialize configuration.");
            log(StringUtil.getErrorStack(e));
            System.exit(-1);
        }
    }

    private void register() throws JingException {
        int count = configCarrier.getCount("init");
        List<Carrier> initCarrierList = new ArrayList<Carrier>();
        for (int i$ = 0; i$ < count; i$++) {
            initCarrierList.add(configCarrier.getCarrier("init", i$));
        }
        int size = initCarrierList.size();
        // sort
        Carrier aCarrier;
        Carrier bCarrier;
        for (int i$ = 0, k$; i$ < size - 1; i$++) {
            aCarrier = initCarrierList.get(i$);
            k$ = i$;
            for (int j$ = i$ + 1; j$ < size; j$++) {
                bCarrier = initCarrierList.get(j$);
                if (Integer.parseInt(aCarrier.getString("index")) > Integer.parseInt(bCarrier.getString("index"))) {
                    initCarrierList.set(k$, bCarrier);
                    initCarrierList.set(j$, aCarrier);
                    k$ = j$;
                }
            }
        }
        // init
        String tempPath;
        for (Carrier tempCarrier : initCarrierList) {
            tempPath = tempCarrier.getString("implements");
            Carrier parameters = null;
            if (tempCarrier.getCount("parameters") > 0) {
                parameters = tempCarrier.getCarrier("parameters");
            }
            Class<?> clazz = ClassUtil.loadClass(tempPath);
            register((Class<? extends JInit>) clazz, parameters);
        }
    }

    /**
     * Description:  <br>
     *     初始化继承了JInit接口的类. <br>
     *     通过这个方式初始化的话, 在整个系统生命力只会初始化一次, 算是个伪造的单例模式. <br>
     *
     * @author: bks <br>
     * @param type <br>
     * @return org.jing.core.lang.itf.JInit <br>
     */
    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public JInit register(Class<? extends JInit> type, Carrier... parameters) {
        if (!jInitMap.containsKey(type)) {
            synchronized (type) {
                if (!jInitMap.containsKey(type)) {
                    try {
                        JInit init = type.newInstance();
                        type.getDeclaredMethod("init", Carrier.class).invoke(init, null != parameters && parameters.length > 0 ? parameters[0] : null);
                        jInitMap.put(type, init);
                        log("Success to register class: {}", type.getName());
                        return init;
                    }
                    catch (Exception e) {
                        log("Failed to register: {}.", type.getName());
                        log(StringUtil.getErrorStack(e));
                        System.exit(-1);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Description:  <br>
     *     获取继承了JInit接口的类的实例. <br>
     *     假如缓存里没有该实例, 直接初始化一个并存入缓存里. <br>
     *
     * @author: bks <br>
     * @param type <br>
     * @return org.jing.core.lang.itf.JInit <br>
     */
    public JInit getRegisterClass(Class<? extends JInit> type, Carrier... parameters) {
        if (!jInitMap.containsKey(type)) {
            synchronized (Configuration.class) {
                if (!jInitMap.containsKey(type)) {
                    return register(type, parameters);
                }
            }
        }
        log("Success to get class: {}", type.getName());
        return jInitMap.get(type);
    }

    public static boolean hasInit() {
        return initFlag;
    }

    public static void setInit() {
        synchronized (Configuration.class) {
            initFlag = true;
        }
    }

    public static void main(String[] args) {
        Configuration.getInstance();
    }
}
