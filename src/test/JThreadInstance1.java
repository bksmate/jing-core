package test;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.lang.itf.JThread;
import org.jing.core.logger.JingLogger;
import org.jing.core.util.StringUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-05-27 <br>
 */
public class JThreadInstance1 implements JThread {
    private static final JingLogger LOGGER = JingLogger.getLogger(JThreadInstance1.class);
    private int size;

    public JThreadInstance1(Carrier param) throws JingException {
        size = StringUtil.parseInteger(param.getStringByName("size", "0"));
    }

    @Override public void run() {
        for (int i$ = 0; i$ < size; i$++) {
            LOGGER.imp("index: {}", i$);
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
