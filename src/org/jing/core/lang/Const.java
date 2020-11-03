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
        + "    <init>\n" + "        <index>1</index>\n"
        + "        <implements>org.jing.core.logger.Log4jInit</implements>\n" + "        <parameters>\n"
        + "\t\t\t<useProperty>true</useProperty>\n" + "            <path>config?log4j.properties</path>\n"
        + "            <rootFilter>org.jing.core.logger.log4j.Log4jFilter</rootFilter>\n" + "        </parameters>\n"
        + "    </init>\n" + "</jing>";

    public static final String SYSTEM_DEFAULT_LOG4J_CONFIG = "log4j.rootLogger=debug,info,debug,warn,error,sql,trace,abc,imp,stdout\n"
        + "\t\t\t\t#console\n" + "\t\t\t\tlog4j.appender.stdout=org.apache.log4j.ConsoleAppender\n"
        + "\t\t\t\tlog4j.appender.stdout.layout=org.apache.log4j.PatternLayout\n"
        + "\t\t\t\tlog4j.appender.stdout.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "\t\t\t\t#info log\n" + "\t\t\t\tlog4j.logger.info=info\n"
        + "\t\t\t\tlog4j.appender.info=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "\t\t\t\tlog4j.appender.info.DatePattern='_'yyyy-MM-dd'.log'\n"
        + "\t\t\t\tlog4j.appender.info.File=${JING_LOG_DIR}/common.log\n" + "\t\t\t\tlog4j.appender.info.Append=true\n"
        + "\t\t\t\tlog4j.appender.info.Threshold=INFO\n"
        + "\t\t\t\tlog4j.appender.info.layout=org.apache.log4j.PatternLayout\n"
        + "\t\t\t\tlog4j.appender.info.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "\t\t\t\t#imp log\n" + "\t\t\t\tlog4j.logger.imp=imp\n"
        + "\t\t\t\tlog4j.appender.imp=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "\t\t\t\tlog4j.appender.imp.DatePattern='_'yyyy-MM-dd'.log'\n"
        + "\t\t\t\tlog4j.appender.imp.File=${JING_LOG_DIR}/common.log\n" + "\t\t\t\tlog4j.appender.imp.Append=true\n"
        + "\t\t\t\tlog4j.appender.imp.Threshold=IMP\n"
        + "\t\t\t\tlog4j.appender.imp.layout=org.apache.log4j.PatternLayout\n"
        + "\t\t\t\tlog4j.appender.imp.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "\t\t\t\t#debug log\n" + "\t\t\t\tlog4j.logger.debug=debug\n"
        + "\t\t\t\tlog4j.appender.debug=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "\t\t\t\tlog4j.appender.debug.DatePattern='_'yyyy-MM-dd'.log'\n"
        + "\t\t\t\tlog4j.appender.debug.File=${JING_LOG_DIR}/common.log\n"
        + "\t\t\t\tlog4j.appender.debug.Append=true\n" + "\t\t\t\tlog4j.appender.debug.Threshold=DEBUG\n"
        + "\t\t\t\tlog4j.appender.debug.layout=org.apache.log4j.PatternLayout\n"
        + "\t\t\t\tlog4j.appender.debug.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "\t\t\t\t#warn log\n" + "\t\t\t\tlog4j.logger.warn=warn\n"
        + "\t\t\t\tlog4j.appender.warn=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "\t\t\t\tlog4j.appender.warn.DatePattern='_'yyyy-MM-dd'.log'\n"
        + "\t\t\t\tlog4j.appender.warn.File=${JING_LOG_DIR}/common.log\n" + "\t\t\t\tlog4j.appender.warn.Append=true\n"
        + "\t\t\t\tlog4j.appender.warn.Threshold=WARN\n"
        + "\t\t\t\tlog4j.appender.warn.layout=org.apache.log4j.PatternLayout\n"
        + "\t\t\t\tlog4j.appender.warn.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "\t\t\t\t#error\n" + "\t\t\t\tlog4j.logger.error=error\n"
        + "\t\t\t\tlog4j.appender.error=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "\t\t\t\tlog4j.appender.error.DatePattern='_'yyyy-MM-dd'.log'\n"
        + "\t\t\t\tlog4j.appender.error.File=${JING_LOG_DIR}/common.log\n"
        + "\t\t\t\tlog4j.appender.error.Append=true\n" + "\t\t\t\tlog4j.appender.error.Threshold=ERROR\n"
        + "\t\t\t\tlog4j.appender.error.layout=org.apache.log4j.PatternLayout\n"
        + "\t\t\t\tlog4j.appender.error.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "\t\t\t\t#trace\n" + "\t\t\t\tlog4j.logger.trace=trace\n"
        + "\t\t\t\tlog4j.appender.trace=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "\t\t\t\tlog4j.appender.trace.DatePattern='_'yyyy-MM-dd'.log'\n"
        + "\t\t\t\tlog4j.appender.trace.File=${JING_LOG_DIR}/common.log\n"
        + "\t\t\t\tlog4j.appender.trace.Append=true\n" + "\t\t\t\tlog4j.appender.trace.Threshold=TRACE\n"
        + "\t\t\t\tlog4j.appender.trace.layout=org.apache.log4j.PatternLayout\n"
        + "\t\t\t\tlog4j.appender.trace.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "\t\t\t\t#sql\n" + "\t\t\t\tlog4j.logger.sql=sql\n"
        + "\t\t\t\tlog4j.appender.sql=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "\t\t\t\tlog4j.appender.sql.DatePattern='_'yyyy-MM-dd'.log'\n"
        + "\t\t\t\tlog4j.appender.sql.File=${JING_LOG_DIR}/sql.log\n" + "\t\t\t\tlog4j.appender.sql.Append=true\n"
        + "\t\t\t\tlog4j.appender.sql.Threshold=SQL\n"
        + "\t\t\t\tlog4j.appender.sql.layout=org.apache.log4j.PatternLayout\n"
        + "\t\t\t\tlog4j.appender.sql.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%c->>-%M][%p] - %m%n\n"
        + "\t\t\t\t#abc\n" + "\t\t\t\tlog4j.logger.abc=abc\n"
        + "\t\t\t\tlog4j.appender.abc=org.jing.core.logger.log4j.impl.Log4jDailyRollingFileAppender\n"
        + "\t\t\t\tlog4j.appender.abc.DatePattern='_'yyyy-MM-dd'.log'\n"
        + "\t\t\t\tlog4j.appender.abc.File=${JING_LOG_DIR}/abc.log\n" + "\t\t\t\tlog4j.appender.abc.Append=true\n"
        + "\t\t\t\tlog4j.appender.abc.Threshold=ABC\n"
        + "\t\t\t\tlog4j.appender.abc.layout=org.apache.log4j.PatternLayout\n"
        + "\t\t\t\tlog4j.appender.abc.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss SSS}][%t][%p] - %m%n";
}
