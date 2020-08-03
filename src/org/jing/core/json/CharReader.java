package org.jing.core.json;

import org.jing.core.lang.ExceptionHandler;
import org.jing.core.lang.JingException;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by code4wt on 17/5/11.
 */
public class CharReader {

    private static final int BUFFER_SIZE = 1024;

    private Reader reader;

    private char[] buffer;

    private int pos;

    private int size;

    public CharReader(Reader reader) {
        this.reader = reader;
        buffer = new char[BUFFER_SIZE];
    }

    /**
     * 返回 pos 下标处的字符，并返回
     */
    public char peek() {
        if (pos - 1 >= size) {
            return (char) -1;
        }

        return buffer[Math.max(0, pos - 1)];
    }

    /**
     * 返回 pos 下标处的字符，并将 pos + 1，最后返回字符
     */
    public char next() throws JingException {
        try {
            if (!hasMore()) {
                return (char) -1;
            }
        }
        catch (Exception e) {
            ExceptionHandler.publish(e);
        }
        return buffer[pos++];
    }

    public void back() {
        pos = Math.max(0, --pos);
    }

    public boolean hasMore() throws JingException {
        try {
            if (pos < size) {
                return true;
            }
            fillBuffer();
            return pos < size;
        }
        catch (Exception e) {
            ExceptionHandler.publish(e);
            return false;
        }
    }

    void fillBuffer() throws IOException {
        int n = reader.read(buffer);
        if (n == -1) {
            return;
        }
        pos = 0;
        size = n;
    }
}
