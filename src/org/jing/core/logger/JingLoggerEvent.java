package org.jing.core.logger;

import org.jing.core.util.DateUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-01-11 <br>
 */
@SuppressWarnings({ "WeakerAccess", "UnusedReturnValue" })
public class JingLoggerEvent {
    private String loggerName;

    private JingLoggerLevel level;

    private String content;

    private boolean stdOut = false;

    public synchronized JingLoggerLevel getLevel() {
        return level;
    }

    public synchronized JingLoggerEvent setLevel(JingLoggerLevel level) {
        this.level = level;
        return this;
    }

    public synchronized JingLoggerEvent setLoggerName(String loggerName) {
        this.loggerName = loggerName;
        return this;
    }

    public synchronized String getContent() {
        return content;
    }

    public synchronized JingLoggerEvent setContent(String content) {
        this.content = content;
        return this;
    }

    public synchronized void generateContent() {
        String template = level.levelConfig.format;
        int length = template.length();
        char c;
        StackTraceElement trace = null;
        StringBuilder stbr = new StringBuilder();
        boolean flag = false;
        for (int i$ = 0; i$ < length; i$++) {
            c = template.charAt(i$);
            if (c == '%') {
                if (!flag) {
                    flag = true;
                }
                else {
                    stbr.append(c);
                }
            }
            else if (flag) {
                switch (c) {
                    // %d - timestamp
                    case 'd':
                        stbr.append(DateUtil.getCurrentDateString(level.levelConfig.dateFormat));
                        break;
                    // %t - threadName
                    case 't':
                        stbr.append(Thread.currentThread().getName());
                        break;
                    // %c - class
                    case 'c':
                        if (null == trace) {
                            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                            trace = stack[stack.length - 1];
                        }
                        stbr.append(null == trace ? "null" : trace.getClassName());
                        break;
                    // %M - method
                    case 'M':
                        if (null == trace) {
                            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                            if (stack.length > 4) {
                                trace = stack[4];
                            }
                            else {
                                trace = stack[stack.length - 1];
                            }
                        }
                        stbr.append(null == trace ? "null" : trace.getMethodName());
                        break;
                    // %l - line
                    case 'l':
                        if (null == trace) {
                            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                            trace = stack[stack.length - 1];
                        }
                        stbr.append(trace.getLineNumber());
                        break;
                    // %p - priority
                    case 'p':
                        stbr.append(level.name);
                        break;
                    // %m - message
                    case 'm':
                        stbr.append(content);
                        break;
                    // %n - newline
                    case 'n':
                        stbr.append(JingLoggerConfiguration.newLine);
                        break;
                    // %N - name
                    case 'N':
                        stbr.append(loggerName);
                        break;
                    default:
                        stbr.append('%').append(c);
                        break;
                }
                flag = false;
            }
            else {
                stbr.append(c);
            }
        }
        this.content = stbr.toString();
    }

    public synchronized void stdOut() {
        if (stdOut) return;
        if (!this.getLevel().isGreaterOrEquals(JingLoggerConfiguration.stdOutLevel)) return;
        stdOut = true;
        System.out.print(content);
    }
}
