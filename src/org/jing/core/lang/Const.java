package org.jing.core.lang;

/**
 * Description: <br>
 *     一些Const. <br>
 *
 * @author: bks <br>
 * @createDate: 2019-01-25 <br>
 */
@SuppressWarnings("unused")
public final class Const {
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

    public static final String SYSTEM_DEFAULT_CONFIG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><jing><init><index>1</index><implements>org.jing.core.logger.local.LoggerFactory</implements><parameters><path>config?logger.xml</path></parameters></init></jing>";

    public static final String SYSTEM_DEFAULT_LOGGER_PARAM = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><jing><path>config?logger.xml</path></jing>";

    public static final String SYSTEM_DEFAULT_LOGGER_CONFIG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><jing><logger><name>ALL</name><appender>file-jing</appender></logger><logger><name>TRACE</name><appender>file-jing</appender></logger><logger><name>DEBUG</name><appender>file-jing</appender></logger><logger><name>INFO</name><appender>file-jing</appender></logger><logger><name>WARN</name><appender>file-jing</appender></logger><logger><name>ERROR</name><appender>file-jing</appender><appender>file-err</appender></logger><logger><name>IMP</name><appender>file-jing</appender></logger><logger><name>SQL</name><appender>file-sql</appender></logger><logger><name>FATAL</name><appender>file-jing</appender></logger><appender><impl>org.jing.core.logger.local.appender.FileAppender</impl><param><file>logs/jing.log</file></param><name>file-jing</name></appender><appender><impl>org.jing.core.logger.local.appender.FileAppender</impl><param><file>logs/sql.log</file></param><name>file-sql</name></appender><appender><impl>org.jing.core.logger.local.appender.FileAppender</impl><param><file>logs/err.log</file></param><name>file-err</name></appender></jing>";
}
