package org.jing.core.lang;

import org.jing.core.lang.itf.JInit;
import org.jing.core.logger.Log4jInit;
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
import java.util.HashSet;
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

    private static String jingHome;

    private static String logDir;

    /**
     * 存储继承了JInit接口的类和对应实例的map. <br>
     */
    private static final Hashtable<Class<? extends JInit>, JInit> jInitMap = new Hashtable<Class<? extends JInit>, JInit>();

    private void loadConfigFile() {
        synchronized (Configuration.class) {
            jingHome = StringUtil.ifEmpty(System.getProperty("JING_HOME"));
            if (StringUtil.isEmpty(jingHome)) {
                jingHome = System.getenv("JING_HOME");
            }
            jingHome = StringUtil.isEmpty(jingHome) ? "" : jingHome + File.separator;
            System.setProperty("JING_HOME", jingHome);
            logDir = System.getProperty("JING_LOG_DIR");
            if (StringUtil.isEmpty(logDir)) {
                logDir = System.getenv("JING_LOG_DIR");
            }
            if (StringUtil.isEmpty(logDir)) {
                logDir = jingHome + "logs";
            }
            System.setProperty("JING_LOG_DIR", logDir);
            String systemConfigFile = FileUtil.buildPathWithHome("config?system.xml");
            String configContent = readFile(systemConfigFile);
            if (StringUtil.isEmpty(configContent)) {
                log("Use default configuration");
                try {
                    configCarrier = CarrierUtil.string2Carrier(Const.SYSTEM_DEFAULT_CONFIG);
                }
                catch (Exception e) {
                    log("Failed to use default configuration.");
                }
            }
            else {
                try {
                    configContent = StringUtil.preOperation4XML(configContent);
                    configCarrier = CarrierUtil.string2Carrier(configContent);
                }
                catch (Exception e) {
                    log("Failed to transfer configuration xml.");
                }
            }
        }
    }

    public static String getJingHome() {
        return jingHome;
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
        }

        return stbr.toString();
    }

    private static void writeFile(String filePath, String content) {
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

    public static void log(String content, String... parameters) {
        content = StringUtil.mixParameters(content, parameters);
        content = getLogPrefix() + content;
        System.out.println(content);
        File logFile = new File(logDir);
        if (logFile.exists() || logFile.mkdirs()) {
            writeFile(logDir + File.separator + "start.log", content);
        }
        else {
            System.out.println("Failed to create start.log, shut down.");
        }
    }

    private static String getLogPrefix() {
        return StringUtil.getDate("[yyyy-MM-dd HH:mm:ss.SSS]") + "===>jing.System: ";
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
        boolean logInit = false;
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
            if (!logInit) {
                aCarrier = initCarrierList.get(i$);
                Class<?> clazz = ClassUtil.loadClass(aCarrier.getString("implements"));
                if (clazz == Log4jInit.class) {
                    logInit = true;
                }
            }
        }
        if (!logInit) {
            Log4jInit log4jInit = new Log4jInit();
            log4jInit.init(CarrierUtil.string2Carrier(Const.SYSTEM_DEFAULT_LOG4J_PARAM));
            // log4jInit.initByString(Const.SYSTEM_DEFAULT_LOG4J_CONFIG);
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
