package org.jing.core.logger.local.dispatcher;

import org.jing.core.logger.local.LocalLoggerConfiguration;
import org.jing.core.logger.local.LocalLoggerEvent;
import org.jing.core.logger.local.appender.BaseAppender;
import org.jing.core.util.StringUtil;

import java.util.ArrayList;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-01-12 <br>
 */
public class BaseDispatcher extends Thread {
    protected final BaseAppender appender;

    protected final ArrayList<LocalLoggerEvent> buffer;

    protected BaseDispatcher(BaseAppender appender) {
        this.appender = appender;
        this.buffer = appender.getBuffer();
    }

    @Override public void run() {
        dispatch();
    }

    protected boolean validate() {
        return true;
    }

    protected void dispatch() {
        boolean isActive = true;
        try {
            while (isActive && validate()) {
                LocalLoggerEvent[] events = null;
                synchronized (this.buffer) {
                    int bufferSize = this.buffer.size();

                    for (isActive = !this.appender.isClosed(); bufferSize == 0 && isActive; isActive = !this.appender.isClosed()) {
                        this.buffer.wait();
                        bufferSize = this.buffer.size();
                    }

                    if (bufferSize > 0) {
                        events = new LocalLoggerEvent[bufferSize];
                        this.buffer.toArray(events);
                        this.buffer.clear();
                        this.buffer.notifyAll();
                    }
                }
                if (events != null) {
                    for (LocalLoggerEvent event : events) {
                        appender.write(event);
                        if (LocalLoggerConfiguration.getGlobalStdOut()) {
                            event.stdOut();
                        }
                    }
                    appender.flush(events);
                }
            }
        }
        catch (Exception e) {
            Thread.currentThread().interrupt();
            System.err.println("Failed to run dispatch: " + Thread.currentThread().getName() +
                LocalLoggerConfiguration.getGlobalNewLine() + StringUtil.getErrorStack(e));
        }
    }
}
