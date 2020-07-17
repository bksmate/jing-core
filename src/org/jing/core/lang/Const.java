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
}
