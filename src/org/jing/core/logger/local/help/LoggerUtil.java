package org.jing.core.logger.local.help;

import org.jing.core.lang.Pair3;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-01-13 <br>
 */
public class LoggerUtil {
    public static Pair3<String, String, String> analysisFileName(String fileName) {
        String prefix, suffix;
        int dotIndex;
        dotIndex = fileName.lastIndexOf(".");
        if (-1 == dotIndex) {
            suffix = "";
        }
        else {
            suffix = fileName.substring(dotIndex + 1);
        }
        fileName = fileName.substring(0, dotIndex);
        dotIndex = fileName.lastIndexOf(".");
        if (-1 == dotIndex) {
            prefix = fileName;
        }
        else {
            prefix = fileName.substring(0, dotIndex);
        }
        fileName = fileName.substring(dotIndex + 1);
        return new Pair3<>(prefix, suffix, fileName);
    }
}
