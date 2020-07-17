package org.jing.core.logger;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Filter;
import org.jing.core.lang.Carrier;
import org.jing.core.lang.Configuration;
import org.jing.core.lang.JingException;
import org.jing.core.lang.itf.JInit;
import org.jing.core.logger.log4j.Log4jLoggerLevel;
import org.jing.core.util.FileUtil;
import org.jing.core.util.StringUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.HashSet;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-02-14 <br>
 */
public class Log4jInit implements JInit, Serializable {
    private static Carrier parameter;

    public static Carrier getParameter() {
        return parameter;
    }

    @Override
    public void init(Carrier params) throws JingException {
        parameter = params;
        PropertyConfigurator.configure(FileUtil.buildPath(parameter.getStringByPath("path")));
        try {
            // 2.1. 加载特殊日志记录级别.
            registerLoggerLevel();
            // 2.2. 绑定Appender.
            bindAppender();
            // 2.3. 加载过滤器.
            bindFilter();
            // 2.4 设置完成标志.
            JingLogger.setInitFlag();
            Configuration.setInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void bindFilter() throws Exception {
        Class<?> filterClass = Class.forName(parameter.getStringByPath("rootFilter"));
        Enumeration es = Logger.getRootLogger().getAllAppenders();
        Filter filter = (Filter) Configuration.getInstance().register((Class<? extends JInit>) filterClass);
        while (es.hasMoreElements()) {
            ((Appender) es.nextElement()).addFilter(filter);
        }
    }

    private void bindAppender() {
        Logger rootLogger = Logger.getRootLogger();
        Enumeration e = rootLogger.getAllAppenders();
        while (e.hasMoreElements()) {
            AppenderSkeleton appender$ = (AppenderSkeleton) e.nextElement();
            Level level$ = Log4jLoggerLevel.LEVEL_MAPPING.get(appender$.getName().toUpperCase());
            if (null != level$) {
                appender$.setThreshold(level$);
            }
        }
    }

    private void registerLoggerLevel() throws Exception {
        Field[] fields$1 = Log4jLoggerLevel.class.getDeclaredFields();
        int count$fields$1 = fields$1.length;
        Field[] fields$2 = Level.class.getDeclaredFields();
        int count$fields$2= fields$2.length;
        Field[] fields = new Field[count$fields$1 + count$fields$2];
        System.arraycopy(fields$1, 0, fields, 0, count$fields$1);
        System.arraycopy(fields$2, 0, fields, count$fields$1, count$fields$2);
        for (Field field$ : fields) {
            if (Modifier.isStatic(field$.getModifiers()) && field$.getType() == Level.class) {
                Log4jLoggerLevel.LEVEL_MAPPING.put(field$.getName(), (Level) field$.get(null));
            }
        }
        String equals = StringUtil.ifEmpty(parameter.getStringByPath("level.equals", ""));
        registerPriority(equals, Log4jLoggerLevel.EQUALS_PRIORITY);
        String gore = StringUtil.ifEmpty(parameter.getStringByPath("logger.level.gore", ""));
        registerPriority(gore, Log4jLoggerLevel.GORE_PRIORITY);
        String ignore = StringUtil.ifEmpty(parameter.getStringByPath("logger.level.ignore", ""));
        registerPriority(ignore, Log4jLoggerLevel.IGNORE_PRIORITY);
    }

    private static void registerPriority(String property, HashSet<Level> hashSet) {
        String[] levels = property.split(",");
        for (String level$ : levels) {
            Level l$ = Log4jLoggerLevel.LEVEL_MAPPING.get(level$);
            if (null != l$) {
                hashSet.add(l$);
            }
        }
    }
}