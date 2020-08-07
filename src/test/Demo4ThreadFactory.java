package test;

import org.jing.core.lang.Pair2;
import org.jing.core.thread.ThreadFactory;

import java.lang.Exception;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class Demo4ThreadFactory {
    private Demo4ThreadFactory() throws Exception {
        ThreadFactory.setMaxThreadNumber(2);
        ThreadFactory.createThreadByType(JThreadInstance3.class, new ThreadFactory.Constructor(new Pair2<Class<?>, Object>(int.class, 4)), new ThreadFactory.Method("run"), true, true);
        ThreadFactory.createThreadByIncident(new JThreadInstance3(6), new ThreadFactory.Method("run"), true, true);
    }

    public static void main(String[] args) throws Exception {
        new Demo4ThreadFactory();
    }
}
