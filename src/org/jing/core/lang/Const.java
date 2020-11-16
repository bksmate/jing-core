package org.jing.core.lang;

/**
 * Description: <br>
 *     一些Const. <br>
 *
 * @author: bks <br>
 * @createDate: 2019-01-25 <br>
 */
@SuppressWarnings("unused")
public class Const {
    /**
     * 起效状态. <br>
     */
    public static final String STATE_ACTIVE = "A";

    /**
     * 失效状态. <br>
     */
    public static final String STATE_INACTIVE = "X";

    /**
     * Carrier的root节点名. <br>
     */
    public final static String CARRIER_ROOT_NODE = "jing";

    /**
     * 系统文件分隔符. <br>
     */
    public final static String SYSTEM_FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * byte数组的最大大小. <br>
     */
    public final static int SYSTEM_MAX_BYTES_SIZE = 100000000;

    /**
     * 暂时没用. <br>
     */
    public final static String CFG_TABLE_LIST_FILE_NAME = "tableList.xml";

    public static final String SYSTEM_DEFAULT_CONFIG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<jing>\n"
        +"</jing>";

    public static final String SYSTEM_DEFAULT_LOG4J_PARAM = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<jing>\n"
        + "\t<rootFilter>org.jing.core.logger.log4j.Log4jFilter</rootFilter>\n" + "</jing>";

    public static final String SYSTEM_DEFAULT_LOG4J_CONFIG = "#you cannot specify every priority with different file for log4j\n"
        + "#\n" + "log4j.rootLogger=debug,info,debug,warn,error,sql,trace,imp,stdout\n" + "\n" + "#console\n"
        + "log4j.appender.stdout=org.apache.log4j.ConsoleAppender\n"
        + "log4j.appender.stdout.layout=org.apache.log4j.PatternLayout\n"
        + "log4j.appender.stdout.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "#info log\n" + "log4j.logger.info=info\n"
        + "log4j.appender.info=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "log4j.appender.info.DatePattern='_'yyyy-MM-dd'.log'\n"
        + "log4j.appender.info.File=${JING_LOG_DIR}/common.log\n" + "log4j.appender.info.Append=true\n"
        + "log4j.appender.info.encoding=UTF-8\n" + "log4j.appender.info.Threshold=INFO\n"
        + "log4j.appender.info.layout=org.apache.log4j.PatternLayout\n"
        + "log4j.appender.info.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "#imp log\n" + "log4j.logger.imp=imp\n"
        + "log4j.appender.imp=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "log4j.appender.imp.DatePattern='_'yyyy-MM-dd'.log'\n"
        + "log4j.appender.imp.File=${JING_LOG_DIR}/common.log\n" + "log4j.appender.imp.Append=true\n"
        + "log4j.appender.imp.encoding=UTF-8\n" + "log4j.appender.imp.Threshold=IMP\n"
        + "log4j.appender.imp.layout=org.apache.log4j.PatternLayout\n"
        + "log4j.appender.imp.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "#debug log\n" + "log4j.logger.debug=debug\n"
        + "log4j.appender.debug=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "log4j.appender.debug.DatePattern='_'yyyy-MM-dd'.log'\n"
        + "log4j.appender.debug.File=${JING_LOG_DIR}/common.log\n" + "log4j.appender.debug.Append=true\n"
        + "log4j.appender.debug.encoding=UTF-8\n" + "log4j.appender.debug.Threshold=DEBUG\n"
        + "log4j.appender.debug.layout=org.apache.log4j.PatternLayout\n"
        + "log4j.appender.debug.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "#warn log\n" + "log4j.logger.warn=warn\n"
        + "log4j.appender.warn=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "log4j.appender.warn.DatePattern='_'yyyy-MM-dd'.log'\n"
        + "log4j.appender.warn.File=${JING_LOG_DIR}/common.log\n" + "log4j.appender.warn.Append=true\n"
        + "log4j.appender.warn.encoding=UTF-8\n" + "log4j.appender.warn.Threshold=WARN\n"
        + "log4j.appender.warn.layout=org.apache.log4j.PatternLayout\n"
        + "log4j.appender.warn.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "#error\n" + "log4j.logger.error=error\n"
        + "log4j.appender.error=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "log4j.appender.error.DatePattern='_'yyyy-MM-dd'.log'\n"
        + "log4j.appender.error.File=${JING_LOG_DIR}/common.log\n" + "log4j.appender.error.Append=true\n"
        + "log4j.appender.error.encoding=UTF-8\n" + "log4j.appender.error.Threshold=ERROR\n"
        + "log4j.appender.error.layout=org.apache.log4j.PatternLayout\n"
        + "log4j.appender.error.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "#trace\n" + "log4j.logger.trace=trace\n"
        + "log4j.appender.trace=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "log4j.appender.trace.DatePattern='_'yyyy-MM-dd'.log'\n"
        + "log4j.appender.trace.File=${JING_LOG_DIR}/common.log\n" + "log4j.appender.trace.Append=true\n"
        + "log4j.appender.trace.encoding=UTF-8\n" + "log4j.appender.trace.Threshold=TRACE\n"
        + "log4j.appender.trace.layout=org.apache.log4j.PatternLayout\n"
        + "log4j.appender.trace.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "#sql\n" + "log4j.logger.sql=sql\n"
        + "log4j.appender.sql=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "log4j.appender.sql.DatePattern='_'yyyy-MM-dd'.log'\n" + "log4j.appender.sql.File=${JING_LOG_DIR}/sql.log\n"
        + "log4j.appender.sql.Append=true\n" + "log4j.appender.sql.encoding=UTF-8\n"
        + "log4j.appender.sql.Threshold=SQL\n" + "log4j.appender.sql.layout=org.apache.log4j.PatternLayout\n"
        + "log4j.appender.sql.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n";
}
