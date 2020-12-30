package test;

import org.jing.core.lang.Const;
import org.jing.core.socket.SocketServer4Demo;
import org.jing.core.socket.SocketServerMenu4Demo;

import java.lang.Exception;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-12-30 <br>
 */
public class Demo4Socket {
    private Demo4Socket() throws Exception {
        // System.out.println(Const.SYSTEM_DEFAULT_CONFIG);
        SocketServerMenu4Demo menu = new SocketServerMenu4Demo(55555, 10, SocketServer4Demo.class);
        menu.execute();
    }

    public static void main(String[] args) throws Exception {
        new Demo4Socket();
    }
}
