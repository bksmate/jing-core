package org.jing.core.socket;

import org.jing.core.logger.JingLogger;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-03-16 <br>
 */
public class SocketServerMenu4Demo extends BaseSocketServerMenu {
    private static final JingLogger LOGGER = JingLogger.getLogger(SocketServerMenu4Demo.class);

    public SocketServerMenu4Demo(int port, int maxNum, Class<? extends BaseSocketServer> clazz) {
        super(port, maxNum, clazz);
    }

    @Override public void execute() {
        this.run();
    }
}
