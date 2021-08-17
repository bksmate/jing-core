package org.jing.core.logger.local.appender;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.logger.local.LocalLoggerEvent;
import org.jing.core.logger.local.dispatcher.BaseDispatcher;

import java.util.ArrayList;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-01-12 <br>
 */
public class BaseAppender {
    protected Carrier paramC;

    protected boolean closed;

    protected final ArrayList<LocalLoggerEvent> buffer;

    protected BaseDispatcher dispatcher;

    protected final Object writeLocker = new Object();

    public BaseAppender(Carrier paramC) throws JingException {
        this.paramC = paramC;
        closed = false;
        this.buffer = new ArrayList<>();
        init();
        createDispatcher();
    }

    @Override protected void finalize() throws Throwable {
        System.out.println("Fin");
        if (!closed) {
            close();
            closed = true;
        }
        super.finalize();
    }

    protected void init() throws JingException {}

    protected void createDispatcher() {
        dispatcher = null;
    }

    public void append(LocalLoggerEvent event) {
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

    public void close() {
        synchronized (this.buffer) {
            this.closed = true;
            this.buffer.notifyAll();
        }
        try {
            if (null != this.dispatcher && this.dispatcher.isAlive()) {
                this.dispatcher.join();
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public ArrayList<LocalLoggerEvent> getBuffer() {
        return this.buffer;
    }

    public Object getWriteLocker() {
        return writeLocker;
    }

    public boolean isClosed() {
        return closed;
    }

    public void write(LocalLoggerEvent event) {}

    public void flush(LocalLoggerEvent[] events) {}
}
