package test;

import org.jing.core.util.GenericUtil;
import sun.misc.URLClassPath;

import java.lang.Exception;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-11-04 <br>
 */
public class Demo4HotDeploy {
    String jarUrl1 = "file:///E:\\W\\WorkSpace\\idea\\JustHelloWorld\\out\\artifacts\\JustHelloWorld_jar\\JustHelloWorld.jar";
    String jarUrl2 = "file:///E:\\W\\WorkSpace\\JAR\\fastjson-1.2.73.jar";
    private Demo4HotDeploy() throws Exception {
        /*callHelloByForName();
        callHello(null);*/
        DynamicJarClassLoader libLoader = new DynamicJarClassLoader(null, ClassLoader.getSystemClassLoader());
        libLoader.addURL(new URL(jarUrl2));
        DynamicJarClassLoader appLoader = new DynamicJarClassLoader(null, libLoader);
        appLoader.addURL(new URL(jarUrl1));
        Field parentField = ClassLoader.class.getDeclaredField("parent");
        parentField.setAccessible(true);
        ClassLoader parentLoader = (ClassLoader) parentField.get(appLoader);
        parentField.set(appLoader, ClassLoader.getSystemClassLoader());
        callHello(appLoader);
        /*removeJarUrlFromSystemClassLoader(jarUrl1);
        addJarUrlToSystemClassLoader(jarUrl1);
        // dynamicJarClassLoader.close();
        callHello(null);*/
        /*test1();
        removeJarUrlFromSystemClassLoader();
        test1();
        showExt();*/
    }

    private DynamicJarClassLoader loadClassByForName(String jarUrlString) throws Exception {
        return new DynamicJarClassLoader(new URL[]{new URL(jarUrlString)});
    }

    private void callHelloByForName() throws Exception {
        DynamicJarClassLoader classLoader = new DynamicJarClassLoader(new URL[]{new URL(jarUrl1)});
        callHello(classLoader);
        classLoader.close();
    }

    private void callHello(DynamicJarClassLoader classLoader) throws Exception {
        Class<?> helloWorldClass;
        if (classLoader == null) {
            helloWorldClass = Class.forName("JustHelloWorld");
        }
        else {
            helloWorldClass = Class.forName("JustHelloWorld", true, classLoader);
        }
        Object object = helloWorldClass.newInstance();
        Method method = helloWorldClass.getDeclaredMethod("json");
        method.invoke(object);
    }

    private void callHelloByClassLoader() throws Exception {
        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
        Class<?> helloWorldClass = systemLoader.loadClass("JustHelloWorld");
        Object object = helloWorldClass.newInstance();
        Method method = helloWorldClass.getDeclaredMethod("json");
        method.invoke(object);
    }

    private void test1() throws Exception {
        while (true) {
            DynamicJarClassLoader classLoader = new DynamicJarClassLoader(new URL[]{new URL(jarUrl1)});
            Class<?> helloWorldClass = classLoader.loadClass("JustHelloWorld");
            Object object = helloWorldClass.newInstance();
            Method method = helloWorldClass.getDeclaredMethod("json");
            method.invoke(object);
            classLoader.close();
            break;
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

    private void addJarUrlToSystemClassLoader(String jarUrlString) throws Exception {
        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
        Class<?> clazz1 = systemLoader.getClass().getSuperclass();
        Method method = clazz1.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        method.invoke(systemLoader, new URL(jarUrlString));
    }

    private void removeJarUrlFromSystemClassLoader(String jarUrlString) throws Exception {
        URL jarUrl = new URL(jarUrlString);
        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
        Class<?> clazz1 = systemLoader.getClass().getSuperclass();
        Field field = clazz1.getDeclaredField("ucp");
        field.setAccessible(true);
        URLClassPath classPath = (URLClassPath) field.get(systemLoader);
        Field pathField = classPath.getClass().getDeclaredField("path");
        Field urlsField = classPath.getClass().getDeclaredField("urls");
        Field loadersField = classPath.getClass().getDeclaredField("loaders");
        Field lmapField = classPath.getClass().getDeclaredField("lmap");
        pathField.setAccessible(true);
        urlsField.setAccessible(true);
        loadersField.setAccessible(true);
        lmapField.setAccessible(true);
        ArrayList<URL> path = (ArrayList<URL>) pathField.get(classPath);
        for (int i$ = 0, count = GenericUtil.countList(path); i$ < count; i$ ++) {
            URL url = path.get(i$);
            if (url.getFile().equalsIgnoreCase(jarUrl.getFile())) {
                path.remove(i$);
                break;
            }
        }
        Stack<URL> urls = (Stack<URL>) urlsField.get(classPath);
        for (int i$ = 0, count = null == urls ? 0 : urls.size(); i$ < count; i$ ++) {
            URL url = urls.get(i$);
            if (url.getFile().equalsIgnoreCase(jarUrl.getFile())) {
                urls.remove(i$);
                break;
            }
        }
        ArrayList<Object> loaders = (ArrayList<Object>) loadersField.get(classPath);
        for (int i$ = 0, count = GenericUtil.countList(loaders); i$ < count; i$ ++) {
            Object jarLoader = loaders.get(i$);
            try {
                Field csuField = jarLoader.getClass().getDeclaredField("csu");
                csuField.setAccessible(true);
                URL url = (URL) csuField.get(jarLoader);
                if (url.getFile().equalsIgnoreCase(jarUrl.getFile())) {
                    Method closeMethod = jarLoader.getClass().getDeclaredMethod("close");
                    closeMethod.setAccessible(true);
                    closeMethod.invoke(jarLoader);
                    loaders.remove(i$);
                    break;
                }
            }
            catch (Exception e) {}
        }
        HashMap<String, Object> lmap = (HashMap<String, Object>) lmapField.get(classPath);
        String jarPath = null;
        for (Map.Entry<String, Object> entry: lmap.entrySet()) {
            URL url = new URL(entry.getKey());
            if (url.getFile().equalsIgnoreCase(jarUrl.getFile())) {
                Object jarLoader = entry.getValue();
                try {
                    Field csuField = jarLoader.getClass().getDeclaredField("csu");
                    csuField.setAccessible(true);
                    url = (URL) csuField.get(jarLoader);
                    if (url.getFile().equalsIgnoreCase(jarUrl.getFile())) {
                        Method closeMethod = jarLoader.getClass().getDeclaredMethod("close");
                        closeMethod.setAccessible(true);
                        closeMethod.invoke(jarLoader);
                        jarPath = entry.getKey();
                        break;
                    }
                }
                catch (Exception e) {}
            }
        }
        if (jarPath != null) {
            lmap.remove(jarPath);
        }

        System.out.println(classPath);
    }

    private void showExt() {
        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
        ClassLoader parentLoader = systemLoader.getParent();
        System.out.println(parentLoader);
    }

    public static void main(String[] args) throws Exception {
        new Demo4HotDeploy();
    }
}
