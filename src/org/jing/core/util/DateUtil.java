package org.jing.core.util;

import org.jing.core.lang.JingException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-01-28 <br>
 */
@SuppressWarnings({ "unused", "WeakerAccess" }) public class DateUtil {
    public static final String DB_DATE_TIME = "YYYY-MM-DD HH24:MI:SS";

    public static final String JAVA_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String JAVA_DATE_NO_UNDERLINE = "yyyyMMdd";

    public static final String JAVA_TIME_NO_UNDERLINE = "HHmmss";

    public static Date getDate(String dateStr, String dateFormat) throws JingException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            return sdf.parse(dateStr);
        }
        catch (ParseException e) {
            throw new JingException(e, "failed to transfer String to Date: [{}] with [{}]", dateStr, dateFormat);
        }
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

    public static Date addDay(Date date, int fieldType, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(fieldType, value);
        return calendar.getTime();
    }

    public static String addDay(String date, String dateFormat, int fieldType, int value) throws JingException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate(date, dateFormat));
        calendar.add(fieldType, value);
        return getDateString(calendar.getTime(), dateFormat);
    }

    public static float getTimeBetween(Date date1, Date date2, int fieldType) throws JingException {
        switch (fieldType) {
            case Calendar.DATE:
                return (float) (date1.getTime() - date2.getTime()) / (float) (1000 * 60 * 60 * 24);
            case Calendar.HOUR:
                return (float) (date1.getTime() - date2.getTime()) / (float) (1000 * 60 * 60);
            case Calendar.MINUTE:
                return (float) (date1.getTime() - date2.getTime()) / (float) (1000 * 60);
            case Calendar.SECOND:
                return (float) (date1.getTime() - date2.getTime()) / (float) (1000);
            default:
                throw new JingException("invalid field type");
        }
    }

    public static float getTimeBetween(String dateString1, String dateFormat1, String dateString2, String dateFormat2, int fieldType) throws JingException {
        return getTimeBetween(DateUtil.getDate(dateString1, dateFormat1), DateUtil.getDate(dateString2, dateFormat2), fieldType);
    }
}
