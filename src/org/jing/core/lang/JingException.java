package org.jing.core.lang;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-11-28 <br>
 */
public class JingException extends BaseJingException {
    public JingException() {
        super();
    }

    public JingException(String message) {
        super(message);
    }

    public JingException(String message, Object... parameters) {
        super(message, parameters);
    }

    public JingException(Throwable cause, String message) {
        super(cause, message);
    }

    public JingException(Throwable cause, String message, Object... parameters) {
        super(cause, message, parameters);
    }

    public JingException(Throwable cause) {
        super(cause);
    }
}
