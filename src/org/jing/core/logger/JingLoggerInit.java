package org.jing.core.logger;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.lang.itf.JInit;
import org.jing.core.logger.itf.JingLoggerFactoryItf;
import org.jing.core.logger.sys.SingleLogger;
import org.jing.core.util.StringUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-16 <br>
 */
public final class JingLoggerInit implements JInit {
    @Override
    public void init(Carrier params) throws JingException {
        // 映射日志框架
        String frame = params.getStringByPath("frame", "");
        frame = StringUtil.ifEmpty(frame).toUpperCase();
        boolean localFlag = false;
        JingLoggerFactoryItf factory = null;
        try {
            if ("LOG4J".equals(frame)) {
                factory = new org.jing.core.logger.log4j.LoggerFactory();
            }
            else {
                localFlag = true;
            }
        }
        catch (Throwable t) {
            SingleLogger.err("failed to create factory by frame: {}", frame);
            SingleLogger.err("use default frame: local");
            SingleLogger.err(StringUtil.getErrorStack(t));
            localFlag = true;
        }
        if (localFlag || !factory.isEnable()) {
            factory = new org.jing.core.logger.local.LoggerFactory();
        }
        // 日志框架初始化
        factory.init(params);
        JingLogger.setFactory(factory);
    }
}
