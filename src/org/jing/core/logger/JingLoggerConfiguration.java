package org.jing.core.logger;

import org.jing.core.lang.Carrier;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-31 <br>
 */
public class JingLoggerConfiguration {
    static Carrier configC;

    static ArrayList<JingLoggerLevel> levelList;

    static JingLoggerLevel rootLevel;

    static HashMap<String, FileOutputStream> writerMap;

    static boolean stdout = true;

    static String dateFormat;

    static String encoding;

    static String format;

    static final String newLine = "\r\n";

    /*public static Carrier getConfigC() {
        return configC;
    }

    public static ArrayList<JingLoggerLevel> getLevelList() {
        return levelList;
    }*/
}
