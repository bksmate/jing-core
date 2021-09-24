package org.jing.core.lang;

import org.jing.core.util.StringUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-24 <br>
 */
@SuppressWarnings({ "unused", "WeakerAccess" })
public class JingRuntimeException extends BaseJingRuntimeException {
    public JingRuntimeException() {
        super();
    }

    public JingRuntimeException(String message) {
        super(message);
    }

    public JingRuntimeException(String message, Object... parameters) {
        super(StringUtil.mixParameters(message, parameters));
    }

    public JingRuntimeException(Throwable cause, String message) {
        super(message, cause);
    }

    public JingRuntimeException(Throwable cause, String message, Object... parameters) {
        super(StringUtil.mixParameters(message, parameters), cause);
    }

    public JingRuntimeException(Throwable cause) {
        super("", cause);
    }
}
