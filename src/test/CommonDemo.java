package test;

import org.jing.core.logger.JingLogger;

import java.lang.Exception;
import java.util.Map;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class CommonDemo {
    private static final JingLogger LOGGER = JingLogger.getLogger(CommonDemo.class);

    public static String getJavaStackTrace() {
        StringBuffer msg = new StringBuffer();
        for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()) {
            Thread thread = (Thread) stackTrace.getKey();
            StackTraceElement[] stack = (StackTraceElement[]) stackTrace.getValue();
            if (thread.equals(Thread.currentThread())) {
                continue;
            }
            msg.append("\n 线程:").append(thread.getName()).append("\n");
            for (StackTraceElement element : stack) {
                msg.append("\t").append(element).append("\n");
            }
        }
        return msg.toString();
    }

    static void test() {

        StackTraceElement stack[] = Thread.currentThread().getStackTrace();

        System.out.println(stack[0]);//java.lang.Thread.getStackTrace

        System.out.println(stack[1]);//本方法的位置及所属类 com.test.A.test(B.java:10)

        System.out.println(stack[2]);//调用本方法的类       com.test.B.main(B.java:4)

        System.out.println("调用本方法是:" + stack[2].getClassName() + "类中的" + stack[2].getMethodName() + "方法");

        System.out.println("调用本方法的文件是" + stack[2].getFileName());

        String callName = stack[2].getClassName();

        System.out.println("调用test方法的类是：" + callName);

        //然后就可以根据调用本方法的类做一些限制或其他操作

        if (callName.endsWith("B")) {

            System.out.println("B类调用了A类的test方法");

        }
        else {

        }

    }

    private CommonDemo() throws Exception {
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
