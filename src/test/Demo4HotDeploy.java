package test;

import org.jing.core.thread.ThreadFactory;

import java.lang.Exception;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-11-04 <br>
 */
public class Demo4HotDeploy {
    private Demo4HotDeploy() throws Exception {
        test1();
    }

    private void test1() throws Exception {
        while (true) {
            DynamicJarClassLoader classLoader = new DynamicJarClassLoader(new URL[]{new URL("file:///F:\\W\\WorkSpace\\IDEA\\JustHelloWorld\\out\\artifacts\\JustHelloWorld_jar\\JustHelloWorld.jar")});
            Class<?> helloWorldClass = classLoader.loadClass("JustHelloWorld");
            Object object = helloWorldClass.newInstance();
            Method method = helloWorldClass.getDeclaredMethod("hello");
            method.invoke(object);
            classLoader.close();
            Thread.sleep(10000);
        }
    }

    private void test2() throws Exception {
        DynamicJarClassLoader classLoader1 = new DynamicJarClassLoader(new URL[]{
            new URL("file:///F:\\W\\WorkSpace\\IDEA\\JustHelloWorld\\out\\artifacts\\JustHelloWorld_jar\\JustHelloWorld.jar")
        });
        DynamicJarClassLoader classLoader2 = new DynamicJarClassLoader(new URL[]{
            new URL("file:///F:\\W\\WorkSpace\\IDEA\\JustHelloWorld\\out\\artifacts\\JustHelloWorld_jar\\JustHelloWorld.jar")
        });
        Class<?> helloWorldClass1 = classLoader1.loadClass("JustHelloWorld");
        Class<?> helloWorldClass2 = classLoader2.loadClass("JustHelloWorld");
        Class<?> helloWorldClass = ClassLoader.getSystemClassLoader().loadClass("JustHelloWorld");
        System.out.println(helloWorldClass);
        ClassLoader defaultClassLoader = ClassLoader.getSystemClassLoader();
    }

    public static void main(String[] args) throws Exception {
        new Demo4HotDeploy();
    }
}
