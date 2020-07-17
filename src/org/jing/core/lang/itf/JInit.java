package org.jing.core.lang.itf;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-02-14 <br>
 */
public interface JInit {
    void init(Carrier params) throws JingException;
}
