package org.jing.core.logger;

import org.jing.core.lang.JingException;
import org.jing.core.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-01-06 <br>
 */
@Deprecated
public class JingLoggerWriter {
    private static class Dispatcher extends Thread {
        private final ArrayList<JingLoggerEvent> buffer;

        private FileOutputStream writer = null;

        private JingLoggerWriter parent;

        Dispatcher(JingLoggerWriter writer) {
            this.parent = writer;
            this.buffer = writer.buffer;
        }

        @Override public void run() {
            boolean isActive = true;
            try {
                while (isActive) {
                    checkWriter();
                    JingLoggerEvent[] events = null;
                    synchronized (this.buffer) {
                        int bufferSize = this.buffer.size();

                        for (isActive = !this.parent.closed; bufferSize == 0 && isActive; isActive = !this.parent.closed) {
                            this.buffer.wait();
                            bufferSize = this.buffer.size();
                        }

                        if (bufferSize > 0) {
                            events = new JingLoggerEvent[bufferSize];
                            this.buffer.toArray(events);
                            this.buffer.clear();
                            this.buffer.notifyAll();
                        }
                    }
                    if (events != null) {
                        for (JingLoggerEvent event : events) {
                            writer.write(event.getContent().getBytes(event.getLevel().levelConfig.encoding));
                            if (JingLoggerConfiguration.stdOut) {
                                event.stdOut();
                            }
                        }
                        writer.flush();
                    }
                }
            }
            catch (Exception e) {
                Thread.currentThread().interrupt();
                System.err.println("Failed to run dispatch: " + Thread.currentThread().getName() + JingLoggerConfiguration.newLine + StringUtil.getErrorStack(e));
            }
        }

        private void checkWriter() throws JingException {
            try {
                if (null == writer || !writer.getFD().valid()) {
                    writer = new FileOutputStream(parent.logFile, true);
                }
                if (!writer.getFD().valid()) {
                    Thread.currentThread().interrupt();
                    throw new JingException("Failed to generate accessible writer");
                }
            }
            catch (Exception e) {
                Thread.currentThread().interrupt();
                throw new JingException("Failed to generate accessible writer");
            }
        }
    }

    private File logFile;

    private final ArrayList<JingLoggerEvent> buffer = new ArrayList<>();

    private volatile Dispatcher dispatcher = null;

    private boolean closed = false;

    JingLoggerWriter(String filePath) throws JingException {
        try {
            logFile = new File(filePath);
            createDispatcher();
        }
        catch (Exception e) {
            throw new JingException("Failed to generate writer", e);
        }
    }

    private void createDispatcher() {
        dispatcher = new Dispatcher(this);
        dispatcher.setName("JingLogger-Dispatcher-" + dispatcher.getName());
        dispatcher.setDaemon(true);
        dispatcher.start();
    }

    void write(JingLoggerEvent event) {
        if (this.dispatcher != null && this.dispatcher.isAlive()) {
            synchronized (this.buffer) {
                int previousSize = this.buffer.size();
                this.buffer.add(event);
                if (previousSize == 0) {
                    this.buffer.notifyAll();
                }
            }
        }
    }

    private void close() {
        synchronized (this.buffer) {
            this.closed = true;
            this.buffer.notifyAll();
        }
        try {
            this.dispatcher.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override protected void finalize() {
        if (!closed) {
            close();
            closed = true;
        }
    }
}
