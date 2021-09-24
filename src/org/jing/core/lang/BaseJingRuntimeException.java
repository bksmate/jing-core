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
@SuppressWarnings({ "WeakerAccess", "Duplicates", "unused" })
public abstract class BaseJingRuntimeException extends RuntimeException implements JException {

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
                    cause = cause.getCause();
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

    public BaseJingRuntimeException() {
        construct(null, null);
    }

    public BaseJingRuntimeException(Throwable throwable) {
        construct(throwable, null);
    }

    public BaseJingRuntimeException(String message) {
        construct(null, message);
    }

    public BaseJingRuntimeException(String message, Object... parameters) {
        construct(null, message, parameters);
    }

    public BaseJingRuntimeException(Throwable throwable, String message) {
        construct(throwable, message);
    }

    public BaseJingRuntimeException(Throwable throwable, String message, Object... parameter) {
        construct(throwable, message, parameter);
    }

    protected void construct(Throwable throwable, String message, Object... parameters) {
        fillInStackTrace();
        setDetailMessage(throwable, message, parameters);
        setCause(throwable);
    }
}
