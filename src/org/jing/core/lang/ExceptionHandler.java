package org.jing.core.lang;

import org.jing.core.logger.JingLogger;
import org.jing.core.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class ExceptionHandler {
    private static JingLogger logger = null;

    public static void publish(String msg) throws JingException {
        if (null != getLogger()) {
            getLogger().error(msg);
        }
        else {
            console(msg);
        }
        if (!Configuration.hasInit()) {
            System.exit(-1);
        }
        throw new JingException(msg);
    }

    public static void publish(Exception e) throws JingException {
        if (null != getLogger()) {
            getLogger().error(StringUtil.getErrorStack(e));
        }
        else {
            console(StringUtil.getErrorStack(e));
        }
        if (!Configuration.hasInit()) {
            System.exit(-1);
        }
        throw new JingException(e);
    }

    public static void publish(String msg, Throwable e) throws JingException {
        if (null != getLogger()) {
            getLogger().error("[{}]\r\n{}", msg, StringUtil.getErrorStack(e));
        }
        else {
            console("[{}]\r\n{}", msg, StringUtil.getErrorStack(e));
        }
        if (!Configuration.hasInit()) {
            System.exit(-1);
        }
        throw new JingException(String.format("[%s]", msg), e);
    }

    public static void publish(String errorCode, String errorMsg, Throwable e) throws JingException {
        if (null != getLogger()) {
            getLogger().error("[{}][{}]\r\n{}", errorCode, errorMsg, StringUtil.getErrorStack(e));
        }
        else {
            console("[{}][{}]\r\n{}", errorCode, errorMsg, StringUtil.getErrorStack(e));
        }
        if (!Configuration.hasInit()) {
            System.exit(-1);
        }
        throw new JingExtraException(errorCode, errorMsg, e);
    }

    public static void publish(String errorCode, String errorMsg) throws JingException {
        if (null != getLogger()) {
            getLogger().error("[{}][{}]", errorCode, errorMsg);
        }
        else {
            console("[{}][{}]", errorCode, errorMsg);
        }
        if (!Configuration.hasInit()) {
            System.exit(-1);
        }
        throw new JingException(String.format("[%s][%s]", errorCode, errorMsg));
    }

    public static void publish(Throwable e, String msg, Object... parameters) throws JingException {
        publish(StringUtil.mixParameters(msg, parameters), e);
    }

    public static void publish(Throwable e, String errorCode, String errorMsg, Object... parameters) throws JingException {
        publish(errorCode, StringUtil.mixParameters(errorMsg, parameters), e);
    }

    public static void publish(String errCode, String errMsg, Object... parameters) throws JingException {
        publish(errCode, StringUtil.mixParameters(errMsg, parameters));
    }

    public static void publishWithCheck(boolean flag, String errorCode, String errorMsg) throws JingException {
        if (flag) {
            ExceptionHandler.publish(errorCode, errorMsg);
        }
    }

    public static void publishWithCheck(boolean flag, String errorCode, String errorMsg, Object... parameters) throws JingException {
        if (flag) {
            ExceptionHandler.publish(errorCode, errorMsg, parameters);
        }
    }

    private static String getLogDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss===");
        return sdf.format(new Date()) + "[%s] [%s]: %s";
    }

    private static void console(String msg, String... params) {
        String res = getLogDateTime();
        res = String.format(res, Thread.currentThread().getName(), Configuration.class.getName(), msg);
        res = res.replaceAll("\\{}", "%s");
        res = String.format(res, (Object[] )params);
        System.out.println(res);
    }

    private static JingLogger getLogger() {
        if (null == logger && JingLogger.hasInit()) {
            logger = JingLogger.getLogger(ExceptionHandler.class);
        }
        return logger;
    }

    public static void main(String[] args) throws JingException {
        ExceptionHandler.publish("COMM-0001", "Error");
    }
}
