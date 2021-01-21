package org.jing.core.lang;

@SuppressWarnings({ "unused", "WeakerAccess" }) public class ExceptionHandler {
    public static void publishIfMatch(boolean condition, String errMsg) throws JingException {
        if (condition) {
            throw new JingException(errMsg);
        }
    }

    public static void publishIfMatch(boolean condition, String errMsg, Object... parameters) throws JingException {
        if (condition) {
            throw new JingException(errMsg, parameters);
        }
    }

    public static void publishExtraIfMatch(boolean condition, String errCode, String errMsg) throws JingExtraException {
        if (condition) {
            throw new JingExtraException(errCode, errMsg);
        }
    }

    public static void publishExtraIfMatch(boolean condition, String errCode, String errMsg, Object... parameters) throws JingExtraException {
        if (condition) {
            throw new JingExtraException(errCode, errMsg, parameters);
        }
    }

    public static void checkNull(Object object) throws JingException {
        checkNull(object, "Null object");
    }

    public static void checkNull(Object object, String errMsg) throws JingException {
        publishIfMatch(object == null, errMsg);
    }

    public static void checkNull(Object object, String errMsg, Object... parameters) throws JingException {
        publishIfMatch(null == object, errMsg, parameters);
    }

    public static void checkNullExtra(Object object, String errCode, String errMsg) throws JingExtraException {
        publishExtraIfMatch(null == object, errCode, errMsg);
    }

    public static void checkNullExtra(Object object, String errCode, String errMsg, Object... parameters) throws JingExtraException {
        publishExtraIfMatch(null == object, errCode, errMsg, parameters);
    }
}
