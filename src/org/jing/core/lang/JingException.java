package org.jing.core.lang;

import org.jing.core.util.StringUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-11-28 <br>
 */
public class JingException extends Exception {
    public JingException() {
        super();
    }

    public JingException(String message) {
        super(message);
    }

    public JingException(String message, Object... parameters) {
        super(StringUtil.mixParameters(message, parameters));
    }

    public JingException(Throwable cause, String message) {
        super(message, cause);
    }

    public JingException(Throwable cause, String message, Object... parameters) {
        super(StringUtil.mixParameters(message, parameters), cause);
    }

    public JingException(Throwable cause) {
        super(cause);
    }
}
