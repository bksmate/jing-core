package test;

import org.jing.core.ext.JClassLoader;

import java.io.File;
import java.lang.Exception;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-11-05 <br>
 */
public class Demo4JClassLoader {
    private Demo4JClassLoader() throws Exception {
        String applicationName = "HelloWorldApplication";
        JClassLoader.getInstance().addLibraryByJarFilePath("fastjson", "E:\\W\\WorkSpace\\JAR\\fastjson-1.2.73.jar");
        while (true) {
            File appJar = new File("E:\\W\\WorkSpace\\idea\\JustHelloWorld\\out\\artifacts\\JustHelloWorld_jar\\JustHelloWorld.jar");
            File localJar = new File("temp\\JustHelloWorld.jar");
            if (!localJar.exists() || (appJar.exists() && appJar.lastModified() != localJar.lastModified())) {
                System.out.println("Find jar file changed");
                int runningNums = JClassLoader.getInstance().getApplicationRunningNums(applicationName);
                System.out.println("Having: " + runningNums);
                if (runningNums <= 0) {
                    System.out.println("stop current application");
                    JClassLoader.getInstance().removeApplication(applicationName);
                    boolean moveFlag = ((localJar.exists() && localJar.delete()) || !localJar.exists()) && appJar.renameTo(localJar);
                    System.out.println("move jar file: " + moveFlag);
                    if (moveFlag) {
                        System.out.println("start application");
                        JClassLoader.getInstance().registerApplication(applicationName, localJar);
                    }
                }
            }
            Thread.sleep(5000);
        }
    }

    public static void main(String[] args) throws Exception {
        new Demo4JClassLoader();
    }
}
