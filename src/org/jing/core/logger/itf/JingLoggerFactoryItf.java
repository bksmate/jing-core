package org.jing.core.logger.itf;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.logger.JingLogger;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-16 <br>
 */
public interface JingLoggerFactoryItf {
    void init(Carrier param) throws JingException;

    boolean isEnable();

    JingLogger getLogger(String name);

    JingLogger getLogger(Class clazz);
}
