package org.jing.core.config;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-22 <br>
 */
public interface JDynamicConfig {
    Carrier readConfigCarrier() throws JingException;
}
