package org.jing.core.logger;

import org.jing.core.lang.JingException;

import java.io.FileOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-01-06 <br>
 */
public class JingLoggerWriter {
    private static class Dispatcher extends Thread {
        // private static final int WAIT_TIMES = 1000;
        private static long serialNo = 0;

        private ConcurrentLinkedQueue<byte[]> bufferQueue;

        private FileOutputStream writer;

        Dispatcher(FileOutputStream writer) {
            bufferQueue = new ConcurrentLinkedQueue<>();
            this.writer = writer;
        }

        void offer(byte[] buffer) {
            bufferQueue.offer(buffer);
        }

        @Override public void run() {
            Thread.currentThread().setName("JingLogger Dispatcher - " + (serialNo ++));
            // int times = 0;
            byte[] buffer;
            // while (times < WAIT_TIMES) {
            while (true) {
                try {
                    while (!bufferQueue.isEmpty()) {
                        // times = 0;
                        buffer = bufferQueue.poll();
                        if (null != buffer && writer.getFD().valid()) {
                            writer.write(buffer);
                            writer.flush();
                        }
                    }
                    // times ++;
                    Thread.sleep(10);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private FileOutputStream writer;

    private volatile Dispatcher dispatcher = null;

    JingLoggerWriter(String filePath) throws JingException {
        try {
            writer = new FileOutputStream(filePath, true);
        }
        catch (Exception e) {
            throw new JingException("Failed to generate writer", e);
        }
    }

    private void createDispatcher() {
        dispatcher = new Dispatcher(writer);
        dispatcher.setDaemon(true);
        dispatcher.start();
    }

    void offer(byte[] buffer) {
        if (null == dispatcher || !dispatcher.isAlive()) {
            createDispatcher();
        }
        dispatcher.bufferQueue.offer(buffer);
    }
}
