package org.jing.core.util;

import org.jing.core.lang.ExceptionHandler;
import org.jing.core.lang.JingException;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-01-17 <br>
 */
@SuppressWarnings({ "unused", "WeakerAccess" })
public class StringUtil {
    /**
     * shouldn't be empty. <br>
     */
    public static final int COMPARE_STRING_NOT_NULL = 2;

    public final static int PAD_MODEL_LEFT = 0;

    public final static int PAD_MODEL_RIGHT = 1;

    /**
     * Description: Get A Default String If Parameter String Is Empty. <br>
     *
     * @author bks <br>
     * @param string <br>
     * @return <br>
     */
    public static String ifWildEmpty(String string) {
        return ifEmpty(string, "");
    }

    /**
     * Description: Get A Default String If Parameter String Is Empty. <br>
     *
     * @author bks <br>
     * @param string <br>
     * @param defaultString <br>
     * @return <br>
     */
    public static String ifWildEmpty(String string, String defaultString) {
        return isEmpty(string) || "null".equalsIgnoreCase(string) ? defaultString : string;
    }

    public static boolean isWildEmpty(String string) {
        return isEmpty(string) || "null".equalsIgnoreCase(string);
    }

    public static boolean isNotWildEmpty(String string) {
        return isNotEmpty(string) && !"null".equalsIgnoreCase(string);
    }

    /**
     * Description: Get A Default String If Parameter String Is Empty. <br>
     *
     * @author bks <br>
     * @param string <br>
     * @param defaultString <br>
     * @return <br>
     */
    public static String ifEmpty(String string, String defaultString) {
        return isEmpty(string) ? defaultString : string;
    }

    /**
     * Description: Get An Empty String If Parameter String Is Empty. <br>
     *
     * @author bks <br>
     * @param string <br>
     * @return <br>
     */
    public static String ifEmpty(String string) {
        return isEmpty(string) ? "" : string;
    }

    /**
     * Description: Get Default String If Parameter String Is Not Empty. <br>
     *
     * @author bks <br>
     * @param string <br>
     * @param defaultString <br>
     * @return <br>
     */
    public static String ifNotEmpty(String string, String defaultString) {
        if (null != string && string.length() != 0) {
            return defaultString;
        }
        else {
            return string;
        }
    }

    /**
     * Description: Judge If String Is Null Or Empty String <br>
     *
     * @author bks <br>
     * @param string <br>
     * @return <br>
     */
    public static boolean isEmpty(String string) {
        return null == string || string.length() == 0;
    }

    /**
     * Description: Judge If String Is Not Null And Empty String <br>
     *
     * @author bks <br>
     * @param string <br>
     * @return <br>
     */
    public static boolean isNotEmpty(String string) {
        return null != string && string.length() != 0;
    }

    /**
     * Description: Repeat String <br>
     *
     * @author bks <br>
     * @param repeatChar <br>
     * @param num <br>
     * @return <br>
     */
    public static String repeat(char repeatChar, int num) {
        return repeat(String.valueOf(repeatChar), num);
    }

    /**
     * Description: Repeat String <br>
     *
     * @author bks <br>
     * @param repeatString <br>
     * @param num <br>
     * @return <br>
     */
    public static String repeat(String repeatString, int num) {
        StringBuilder stbr = new StringBuilder();
        for (int i = 0; i < num; i++) {
            stbr.append(repeatString);
        }
        return stbr.toString();
    }

    /**
     * Description: Cut Or Add. <br>
     *
     * @author bks <br>
     * @param string <br>
     * @param model <br>
     * @param c <br>
     * @return <br>
     */
    public static String pad(String string, int model, char c, int length) {
        if (string.length() > length) {
            if (model == PAD_MODEL_LEFT) {
                return string.substring(string.length() - length);
            }
            else if (model == PAD_MODEL_RIGHT) {
                return string.substring(0, length);
            }
            else {
                return string;
            }
        }
        else {
            if (model == PAD_MODEL_LEFT) {
                return repeat(c, length - string.length()) + string;
            }
            else if (model == PAD_MODEL_RIGHT) {
                return string + repeat(c, length - string.length());
            }
            else {
                return string;
            }
        }
    }

    public static String getErrorStack(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        try {
            pw.close();
            sw.close();
        }
        catch (Exception ignored) {}
        return sw.toString();
    }

    /**
     * Description: 以{}替换占位符的字符串. <br>
     *
     * @param origString <br>
     * @param strings <br>
     * @return <br>
     */
    public static String setPlaceHolder(String origString, String... strings) {
        if (null != strings && strings.length > 0) {
            String newString = origString;
            for (String s$ : strings) {
                newString = newString.replaceFirst("\\{}", s$);
            }
            return newString;
        }
        return origString;
    }

    /**
     * 去掉可能有的空格/BOM头. <br>
     *
     * @param xml <br>
     * @return <br>
     */
    public static String preOperation4XML(String xml) {
        int length = xml.length();
        for (int i$ = 0; i$ < length; i$++) {
            if ('<' == xml.charAt(i$)) {
                return xml.substring(i$);
            }
        }
        return xml;
    }

    public static String mixParameters(String content, Object... parameters) {
        if (null != parameters) {
            content = content.replaceAll("%", "%%");
            content = content.replaceAll("\\{}", "%s");
            content = String.format(content, parameters);
        }
        return content;
    }

    /**
     * Description: Remove All Space[" "], Line["-"] And UnderLine["_"]. <br>
     *
     * @author bks <br>
     * @param string <br>
     * @return <br>
     */
    public static String removeSpaceAndLine(String string) {
        return StringUtil.ifEmpty(string).replaceAll("[\\s\\-_]", "");
    }

    /**
     * Description: Compare String. <br>
     * For option: <br>
     * 0: ignore Space[" "], Line["-"] And UnderLine["_"]. <br>
     * 1: ignore Space[" "], Line["-"] ,UnderLine["_"] And Case. <br>
     * 2: shouldn't be empty. <br>
     * Else: Common Comparation. <br>
     *
     * @author bks <br>
     * @param string1 <br>
     * @param string2 <br>
     * @param options <br>
     * @return <br>
     */
    public static boolean compareString(String string1, String string2, int options) {
        switch (options) {
            // ignore Space[" "], Line["-"] And UnderLine["_"].
            case 0:
                return removeSpaceAndLine(string1).equals(removeSpaceAndLine(string2));
            // ignore Space[" "], Line["-"] ,UnderLine["_"] And Case.
            case 1:
                return removeSpaceAndLine(string1).equalsIgnoreCase(removeSpaceAndLine(string2));
            // shouldn't be empty.
            case COMPARE_STRING_NOT_NULL:
                return null != string1 && null != string2 && string1.equals(string2);
            // Common.
            default:
                return StringUtil.ifEmpty(string1).equals(string2);
        }
    }

    /**
     * Description: Transfer String To Integer Without Exception. <br>
     * If failed, it will return defaultValue. If defaultValue was not given, it will return 0. <br>
     *
     * @author bks <br>
     * @param string <br>
     * @param defaultInteger <br>
     * @return <br>
     */
    public static int parseInteger(String string, int defaultInteger) {
        int retInt = defaultInteger;
        try {
            retInt = Integer.parseInt(string);
            return retInt;
        }
        catch (Exception e) {
            return retInt;
        }
    }

    public static long parseLong(String string) {
        return parseLong(string, 0);
    }

    public static long parseLong(String string, long defaultLong) {
        long retLong = defaultLong;
        try {
            retLong = Long.parseLong(string);
            return retLong;
        }
        catch (Exception e) {
            return retLong;
        }
    }

    public static float parseFloat(String string) {
        return parseFloat(string, 0);
    }

    public static float parseFloat(String string, float defaultFloat) {
        float retFloat = defaultFloat;
        try {
            retFloat = Float.parseFloat(string);
            return retFloat;
        }
        catch (Exception e) {
            return retFloat;
        }
    }

    public static double parseDouble(String string) {
        return parseDouble(string, 0);
    }

    public static double parseDouble(String string, double defaultDouble) {
        double retDouble = defaultDouble;
        try {
            retDouble = Double.parseDouble(string);
            return retDouble;
        }
        catch (Exception e) {
            return retDouble;
        }
    }

    /**
     * Description: Transfer String To Integer Without Exception. <br>
     * If failed, it will return 0. <br>
     *
     * @author bks <br>
     * @param string <br>
     * @return <br>
     */
    public static int parseInteger(String string) {
        return parseInteger(string, 0);
    }

    /**
     * Description: Transfer Object To String Without Exception<br>
     *
     * @author bks <br>
     * @param object <br>
     * @return <br>
     */
    public static String parseString(Object object) {
        String outPutString;
        try {
            outPutString = (String) object;
        }
        catch (Exception e1) {
            try {
                outPutString = String.valueOf(object);
            }
            catch (Exception e2) {
                try {
                    outPutString = object.toString();
                }
                catch (Exception e3) {
                    outPutString = "";
                }
            }
        }
        return outPutString;
    }

    public static String cutString(String origString, int length) {
        origString = ifEmpty(origString);
        if (origString.length() <= length) {
            return origString;
        }
        else {
            return origString.substring(0, length);
        }
    }

    public static <K, V> String getMapString(Map<K, V> map, K key) {
        if (null == map) {
            return "";
        }
        Object value = map.containsKey(key) ? map.get(key) : null;
        if (null == value) {
            return "";
        }
        else {
            String retString = parseString(value);
            return ifEmpty(retString);
        }
    }

    public static String escape4Json(String string) {
        string = string
            .replaceAll("\\r", "\\\\r")
            .replaceAll("\\n", "\\\\n")
            .replaceAll("\"", "\\\\\"")
            .replaceAll("\\t", "\\\\t")
            .replace("\\", "\\\\");
        return string;
    }

    public static String readFromInputStream(InputStream inputStream, String charSet) throws JingException {
        return readFromInputStream(inputStream, 3072, charSet);
    }

    public static String readFromInputStream(InputStream inputStream, int bufferSize, String charSet) throws JingException {
        try {
            byte[] buffer = new byte[bufferSize];
            StringBuilder stbr = new StringBuilder();
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                stbr.append(new String(buffer, 0, length, charSet));
                if (0 == inputStream.available()) {
                    break;
                }
            }
            return stbr.toString();
        }
        catch (Exception e) {
            throw new JingException(e);
        }
    }
}
