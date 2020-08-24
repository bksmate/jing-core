package test;

import org.apache.log4j.Level;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-08-24 <br>
 */
public class TempLoggerLevel extends Level {
    public static TempLoggerLevel ABC = new TempLoggerLevel(39998, "ABC",0);

    protected TempLoggerLevel(int level, String levelStr, int syslogEquivalent) {
        super(level, levelStr, syslogEquivalent);
    }
}
