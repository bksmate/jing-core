package test;

import org.jing.core.lang.GlobalContext;
import org.jing.core.lang.JingException;

import java.io.InputStream;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-24 <br>
 */
public class TempGlobalContext extends GlobalContext {
    public static void setInputStream(InputStream inputStream) throws JingException {
        GlobalContext.addCloseable("INPUT", inputStream);
    }
}
