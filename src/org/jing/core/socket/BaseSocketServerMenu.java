package org.jing.core.socket;

import org.jing.core.lang.itf.JThread;
import org.jing.core.logger.JingLogger;
import org.jing.core.util.StringUtil;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-09-25 <br>
 */
public abstract class BaseSocketServerMenu implements JThread {
    private static final JingLogger LOGGER = JingLogger.getLogger(BaseSocketServerMenu.class);

    private static final HashMap<Thread, String> THREAD_BATCH_MAP = new HashMap<Thread, String>();

    private Class<? extends BaseSocketServer> socketServerClazz;

    protected int port;

    protected int maxThreadNum;

    public BaseSocketServerMenu(int port, int maxThreadNum, Class<? extends BaseSocketServer> clazz) {
        this.port = port;
        this.maxThreadNum = maxThreadNum;
        this.socketServerClazz = clazz;
    }

    public static boolean addBatch(String batchNo) {
        if (StringUtil.isEmpty(batchNo)) {
            return true;
        }
        synchronized (BaseSocketServerMenu.class) {
            for (Map.Entry<Thread, String> entry : THREAD_BATCH_MAP.entrySet()) {
                if (entry.getValue().equals(batchNo)) {
                    LOGGER.imp("Batch has already existed in batchSet. [batchNo: {}]", batchNo);
                    return false;
                }
            }
            THREAD_BATCH_MAP.put(Thread.currentThread(), batchNo);
            LOGGER.imp("Add batch into batchSet. [batchNo: {}]", batchNo);
            return true;
        }
    }

    protected String getSocketServerName() {
        return this.getClass().getName();
    }

    public static void removeBatch() {
        synchronized (BaseSocketServerMenu.class) {
            String batchNo = THREAD_BATCH_MAP.get(Thread.currentThread());
            if (StringUtil.isNotEmpty(batchNo)) {
                LOGGER.imp("Remove batch info. [batchNo: {}]", batchNo);
                THREAD_BATCH_MAP.remove(Thread.currentThread());
            }
        }
    }

    @Override public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            ExecutorService executor = Executors.newCachedThreadPool();
            if (maxThreadNum <= 0) {
                maxThreadNum = Integer.MAX_VALUE;
            }
            LOGGER.imp("Start {} with [port: {}][permits: {}]", getSocketServerName(), port, maxThreadNum);
            final Semaphore semaphore = new Semaphore(maxThreadNum);
            Socket client;
            boolean flag = true;
            while (flag) {
                client = server.accept();
                BaseSocketServer socketServer = socketServerClazz.newInstance();
                socketServer.init(client, semaphore, true);
                executor.execute(socketServer);
            }
            executor.shutdown();
            server.close();
        }
        catch (Exception e) {
            LOGGER.error("Failed to start...");
            e.printStackTrace();
        }
    }

    public abstract void execute();
}
