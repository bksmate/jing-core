package org.jing.core.socket;

import org.jing.core.lang.Configuration;
import org.jing.core.logger.JingLogger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-09-25 <br>
 */
public abstract class BaseSocketServer implements Runnable {
    private static final JingLogger LOGGER = JingLogger.getLogger(BaseSocketServer.class);

    protected Socket client;

    protected Semaphore num;

    protected boolean needReload;

    public BaseSocketServer() {}

    protected String getSubSocketServerName() {
        return this.getClass().getName();
    }

    public void init(Socket client, Semaphore num, boolean needReload) {
        this.client = client;
        this.num = num;
        this.needReload = needReload;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (null != client && !client.isClosed()) {
            try {
                client.close();
            } catch (Throwable ignored) {}
        }
    }

    public void run() {
        try (
            InputStream reader = client.getInputStream();
            OutputStream writer = client.getOutputStream()
            )
        {
            LOGGER.imp("[{}: {}] start. [ip: {}][port: {}][availablePermits: {}]",
                getSubSocketServerName(), hashCode(),
                client.getInetAddress().getHostAddress(), client.getPort(), num.availablePermits());
            num.acquire();
            if (needReload) {
                Configuration.getInstance().reloadConfigFile();
            }
            operation(reader, writer);
        } catch (Exception e) {
            LOGGER.error("Failed to run Socket Server: {}", e, this,hashCode());
            e.printStackTrace();
        }
        finally {
            num.release();
            if (null != client && !client.isClosed()) {
                try {
                    client.close();
                    LOGGER.imp("[{}: {}] bye and release...", getSubSocketServerName(), this.hashCode());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            BaseSocketServerMenu.removeBatch();
        }
    }

    protected void reload() {
        Configuration.getInstance().reloadConfigFile();
    }

    protected abstract void operation(InputStream reader, OutputStream writer) throws Exception;

    public void writeAdvance(String content, String charSet) throws Exception {
        OutputStream writer = client.getOutputStream();
        writer.write(content.getBytes(charSet));
        writer.flush();
    }

    public void closeAdvance() throws Exception {
        if (null != client && !client.isClosed()) {
            client.close();
        }
    }
}
