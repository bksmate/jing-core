package test;

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
        JThreadInstance3 thread1 = new JThreadInstance3(10);
        JThreadInstance3 thread2 = new JThreadInstance3(10);
        JThreadInstance3 thread3 = new JThreadInstance3(10);
        JThreadInstance4 thread4 = new JThreadInstance4(10);
        JThreadInstance4 thread5 = new JThreadInstance4(10);
        JThreadInstance4 thread6 = new JThreadInstance4(10);
        ThreadFactory.setMaxThreadNumber(1);
        ThreadFactory.createThread(thread1, "run", true, true);
        ThreadFactory.createThread(thread2, "run", true, false);
        ThreadFactory.createThread(thread3, "run", true, true);
        ThreadFactory.createThread(thread4, "run", true, false);
        ThreadFactory.createThread(thread5, "run", true, true);
        ThreadFactory.createThread(thread6, "run", true, true);
    }

    public static void main(String[] args) throws Exception {
        new Demo4ThreadFactory();
    }
}
