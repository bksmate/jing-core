package test;

import org.jing.core.lang.JingException;
import org.jing.core.logger.JingLogger;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-05-27 <br>
 */
public class JThreadInstance4 {
    private static final JingLogger LOGGER = JingLogger.getLogger(JThreadInstance4.class);
    private int size = 0;

    public JThreadInstance4(int size) throws JingException {
        this.size = size;
    }

    public void run() {
        for (int i$ = 0; i$ < size; i$++) {
            LOGGER.imp("index: {}", i$ * 2 + 1);
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
