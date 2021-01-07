package org.jing.core.lang;

import org.jing.core.util.StringUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-12 <br>
 */
public class JingExtraException extends JingException {
    private static String ERR_MSG_FORMAT = "[%s][%s]";

    private String errCode = null;

    private String errMsg = null;

    public JingExtraException() {
        super();
    }

    public JingExtraException(String errCode, String errMsg) {
        super(String.format(ERR_MSG_FORMAT, errCode, errMsg));
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public JingExtraException(String errCode, String errMsg, Object... parameters) {
        super(String.format(ERR_MSG_FORMAT, errCode, StringUtil.mixParameters(errMsg, parameters)));
        this.errCode = errCode;
        this.errMsg = StringUtil.mixParameters(errMsg, parameters);
    }

    public JingExtraException(Throwable throwable, String errCode, String errMsg) {
        super(String.format(ERR_MSG_FORMAT, errCode, errMsg), throwable);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public JingExtraException(Throwable throwable, String errCode, String errMsg, Object parameters) {
        super(String.format(ERR_MSG_FORMAT, errCode, StringUtil.mixParameters(errMsg, parameters)), throwable);
        this.errCode = errCode;
        this.errMsg = StringUtil.mixParameters(errMsg, parameters);
    }

    public JingExtraException(Throwable throwable) {
        super(throwable);
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public static void setGlobalErrMsgFormat(String errMsgFormat) {
        if (StringUtil.isEmpty(errMsgFormat) || !errMsgFormat.contains("%s")) {
            return;
        }
        ERR_MSG_FORMAT = errMsgFormat;
    }
}
