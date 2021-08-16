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
public class ByteUtil {
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
            throw new JingException("Invalid charset: {}", charset);
        }
    }

    public static void main(String[] args) throws JingException {
        String content = "中文";
        String charset = "utf-8";
        byte[] buffer = string2ByteArray(content, charset, 10, (byte) 0);
        System.out.println(buffer);
    }
}
