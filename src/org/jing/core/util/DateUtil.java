package org.jing.core.util;

import org.jing.core.lang.ExceptionHandler;
import org.jing.core.lang.JingException;
import org.jing.core.logger.JingLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-01-28 <br>
 */
public class DateUtil {
    private static JingLogger LOGGER = JingLogger.getLogger(DateUtil.class);

    public static final String DB_DATE_TIME = "YYYY-MM-DD HH24:MI:SS";

    public static final String JAVA_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static Date getDate(String dateStr, String dateFormat) throws JingException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            return sdf.parse(dateStr);
        }
        catch (ParseException e) {
            ExceptionHandler.publish("DATE-00000", "Failed to transfer String to Date", e);
        }
        return null;
    }

    public static java.sql.Date getSqlDate(String dateStr, String dateFormat) throws JingException {
        return new java.sql.Date(getDate(dateStr, dateFormat).getTime());
    }

    public static String getDateString(Date date, String dateFormat) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    public static String getSqlDateString(java.sql.Date date, String dateFormat) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    public static String getCurrentDateString(String dateFormat) {
        return getDateString(new Date(), dateFormat);
    }

    public static String getCurrentDateString() {
        return getDateString(new Date(), JAVA_DATE_TIME);
    }
}
