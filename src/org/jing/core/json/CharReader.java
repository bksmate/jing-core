package org.jing.core.json;

import org.jing.core.lang.JingException;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by code4wt on 17/5/11.
 */
@SuppressWarnings("BooleanMethodIsAlwaysInverted") public class CharReader {

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
    char peek() {
        if (pos - 1 >= size) {
            return (char) -1;
        }

        return buffer[Math.max(0, pos - 1)];
    }

    /**
     * 返回 pos 下标处的字符，并将 pos + 1，最后返回字符
     */
    char next() throws JingException {
        try {
            if (!hasMore()) {
                return (char) -1;
            }
        }
        catch (Exception e) {
            throw new JingException(e, e.getMessage());
        }
        return buffer[pos++];
    }

    void back() {
        pos = Math.max(0, --pos);
    }

    boolean hasMore() throws JingException {
        try {
            if (pos < size) {
                return true;
            }
            fillBuffer();
            return pos < size;
        }
        catch (Exception e) {
            throw new JingException(e, e.getMessage());
        }
    }

    private void fillBuffer() throws IOException {
        int n = reader.read(buffer);
        if (n == -1) {
            return;
        }
        pos = 0;
        size = n;
    }
}
