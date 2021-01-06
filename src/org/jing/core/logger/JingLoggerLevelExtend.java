package org.jing.core.logger;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-31 <br>
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class JingLoggerLevelExtend extends JingLoggerLevel {
    protected JingLoggerLevelExtend(int priority, String name, boolean synchronize) {
        super(priority, name, synchronize);
    }

    protected JingLoggerLevelExtend(int priority, String name) {
        super(priority, name);
    }

    public static final JingLoggerLevelExtend SQL = new JingLoggerLevelExtend(39999, "SQL", true);
}
