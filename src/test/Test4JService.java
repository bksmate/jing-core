package test;

import org.jing.core.lang.JingException;
import org.jing.core.lang.annotation.ServiceCode;
import org.jing.core.lang.itf.JService;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-06-29 <br>
 */
@ServiceCode("test")
public class Test4JService implements JService {
    @Override public Object execute(Object param) throws JingException {
        return null;
    }
}
