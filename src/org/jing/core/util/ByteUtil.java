package org.jing.core.util;

import org.jing.core.lang.JingException;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-07-21 <br>
 */
@SuppressWarnings("unused") public class ByteUtil {
    public static byte[] string2ByteArray(String content, String charset, int arrLength, byte fillByte)
        throws JingException {
        try {
            byte[] buffer = StringUtil.ifEmpty(content).getBytes(charset);
            int length = buffer.length;
            if (arrLength < length) {
                buffer = Arrays.copyOfRange(buffer, 0, arrLength);
            }
            else if (arrLength > length) {
                buffer = Arrays.copyOf(buffer, arrLength);
                Arrays.fill(buffer, length, arrLength, fillByte);
            }
            return buffer;
        }
        catch (UnsupportedEncodingException e) {
            throw new JingException("invalid charset: {}", charset);
        }
    }
}
