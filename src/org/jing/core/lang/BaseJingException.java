package org.jing.core.lang;

import org.jing.core.lang.itf.JException;
import org.jing.core.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-24 <br>
 */
@SuppressWarnings({ "WeakerAccess", "unused", "Duplicates" })
public abstract class BaseJingException extends Exception implements JException {

    private void setDetailMessage(String detailMessage) {
        try {
            Field field = Throwable.class.getDeclaredField("detailMessage");
            field.setAccessible(true);
            field.set(this, detailMessage);
        }
        catch (Throwable ignored) {}
    }

    public void setDetailMessage(Throwable throwable, String message, Object... parameters) {
        String detailMessage;
        if (null == message && null == throwable) {
            detailMessage = null;
        }
        else if (null == message) {
            if (throwable instanceof JException) {
                detailMessage = throwable.getMessage();
            }
            else if (throwable instanceof InvocationTargetException) {
                Throwable cause = ((InvocationTargetException) throwable).getTargetException();
                if (cause instanceof JException) {
                    if (null != cause.getCause()) {
                        cause = cause.getCause();
                    }
                }
                detailMessage = StringUtil.ifEmpty(cause.getMessage(), cause.getLocalizedMessage());
            }
            else {
                detailMessage = null;
            }
        }
        else {
            detailMessage = StringUtil.mixParameters(message, parameters);
        }
        setDetailMessage(detailMessage);
    }

    public void setCause(Throwable throwable) {
        Throwable cause;
        if (null == throwable) {
            cause = this;
        }
        else if (throwable instanceof JException) {
            cause = throwable.getCause();
        }
        else if (throwable instanceof InvocationTargetException) {
            cause = ((InvocationTargetException) throwable).getTargetException();
            if (cause instanceof JException) {
                cause = cause.getCause();
            }
        }
        else {
            cause = throwable;
        }
        try {
            Field field = Throwable.class.getDeclaredField("cause");
            field.setAccessible(true);
            field.set(this, cause);
        }
        catch (Throwable ignored) {}
    }

    public BaseJingException() {
        construct(null, null);
    }

    public BaseJingException(Throwable throwable) {
        construct(throwable, null);
    }

    public BaseJingException(String message) {
        construct(null, message);
    }

    public BaseJingException(String message, Object... parameters) {
        construct(null, message, parameters);
    }

    public BaseJingException(Throwable throwable, String message) {
        construct(throwable, message);
    }

    public BaseJingException(Throwable throwable, String message, Object... parameter) {
        construct(throwable, message, parameter);
    }

    protected void construct(Throwable throwable, String message, Object... parameters) {
        fillInStackTrace();
        setDetailMessage(throwable, message, parameters);
        setCause(throwable);
    }

    public static void main(String[] args) throws Exception {
        throw new JingException(new JingRuntimeException("123"));
    }
}
