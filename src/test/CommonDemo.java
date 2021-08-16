package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class CommonDemo {
    private CommonDemo() throws Exception {
        Socket socket = new Socket("127.0.0.1", 15999);
        OutputStream writer = socket.getOutputStream();
        InputStream reader = socket.getInputStream();
        writer.write("123123".getBytes("GBK"));
        writer.flush();
        byte[] buffer = new byte[1024];
        int pos;
        StringBuilder stbr = new StringBuilder();
        while (-1 != (pos = reader.read(buffer))) {
            stbr.append(new String(buffer, 0, pos, "gbk"));
        }
        String response = stbr.toString();
        reader.close();
        writer.close();
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
