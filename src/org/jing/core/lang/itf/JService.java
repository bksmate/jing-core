package org.jing.core.lang.itf;

import org.jing.core.lang.JingException;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-03-21 <br>
 */
public interface JService {
    Object execute(Object param) throws JingException;
}
