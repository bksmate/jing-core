package org.jing.core.util;

import org.jing.core.lang.JingException;
import org.jing.core.lang.JingRuntimeException;
import org.jing.core.lang.MethodInformation;
import org.jing.core.lang.Pair2;
import org.jing.core.lang.itf.JService;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static java.lang.Thread.currentThread;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-03-21 <br>
 */
@SuppressWarnings({ "WeakerAccess", "unused", "unchecked", "Duplicates" })
public class ClassUtil {
    private static ClassLoader defaultClassLoader;

    private static ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

    public static ClassLoader[] getClassLoader(Class<?> clazz) {
        return new ClassLoader[] { null, defaultClassLoader,
            currentThread().getContextClassLoader(), ClassUtil.class.getClassLoader(), systemClassLoader
        };
    }

    public static Class<?> loadClass(String fullClassName, ClassLoader loader) throws JingException {
        try {
            if (null == loader || null == fullClassName || fullClassName.length() == 0) {
                return null;
            }
            else {
                return loader.loadClass(fullClassName);
            }
        }
        catch (ClassNotFoundException e) {
            throw new JingException(e, "failed to load class [fullClassName: {}][loader: {}]", fullClassName, loader);
        }
    }

    public static Class<?> loadClass(String fullClassName, ClassLoader[] loaders) throws JingException {
        if (null == loaders || null == fullClassName || fullClassName.length() == 0) {
            return null;
        }
        else {
            int count = loaders.length;
            for (ClassLoader loader$ : loaders) {
                if (null != loader$) {
                    Class<?> clazz = loadClass(fullClassName, loader$);
                    if (null != clazz) {
                        return clazz;
                    }
                }
            }
            return null;
        }
    }

    public static Class<?> loadClass(String fullClassName) throws JingException {
        return loadClass(fullClassName, getClassLoader(ClassUtil.class));
    }

    public static void findClassesByFile(String pkgName, String pkgPath, Set<Class<?>> classes) throws JingException {
        // 获取此包的目录 建立一个File
        File dir = new File(pkgPath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles(new FileFilter() {
            @Override public boolean accept(File pathName) {
                return  pathName.isDirectory() || pathName.getName().endsWith("class");
            }
        });

        if (null == files || files.length == 0) {
            return;
        }

        String className;
        Class clz;
        // 循环所有文件
        for (File f : files) {
            // 如果是目录 则继续扫描
            if (f.isDirectory()) {
                findClassesByFile(pkgName + "." + f.getName(), pkgPath + "/" + f.getName(), classes);
                continue;
            }
            // 如果是java类文件 去掉后面的.class 只留下类名
            className = f.getName();
            className = className.substring(0, className.length() - 6);

            //加载类
            clz = loadClass(pkgName + "." + className);
            // 添加到集合中去
            if (clz != null) {
                classes.add(clz);
            }
        }
    }

    public static void findClassesByJar(String pkgName, JarFile jar, Set<Class<?>> classes) throws JingException {
        String pkgDir = pkgName.replace(".", "/");
        // 从此jar包 得到一个枚举类
        Enumeration<JarEntry> entry = jar.entries();

        JarEntry jarEntry;
        String name, className;
        Class<?> clazz;
        // 同样的进行循环迭代
        while (entry.hasMoreElements()) {
            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文
            jarEntry = entry.nextElement();

            name = jarEntry.getName();
            // 如果是以/开头的
            if (name.charAt(0) == '/') {
                // 获取后面的字符串
                name = name.substring(1);
            }

            if (jarEntry.isDirectory() || !name.startsWith(pkgDir) || !name.endsWith(".class")) {
                continue;
            }
            //如果是一个.class文件 而且不是目录
            // 去掉后面的".class" 获取真正的类名
            className = name.substring(0, name.length() - 6);
            //加载类
            clazz = loadClass(className.replace("/", "."));
            // 添加到集合中去
            if (clazz != null) {
                classes.add(clazz);
            }
        }
    }

    public static Set<Class<?>> getClassByPackage(String packagePath) throws JingException {
        //第一个class类的集合
        Set<Class<?>> classes = new HashSet<>();
        // 获取包的名字 并进行替换
        String pkgDirName = packagePath.replace('.', '/');
        ClassLoader[] loaders = getClassLoader(ClassUtil.class);
        try {
            for (ClassLoader loader$ : loaders) {
                if (null == loader$) {
                    continue;
                }
                Enumeration<URL> urls = loader$.getResources(pkgDirName);
                if (null == urls) {
                    continue;
                }
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    // 得到协议的名称
                    String protocol = url.getProtocol();
                    // 如果是以文件的形式保存在服务器上
                    if ("file".equals(protocol)) {
                        // 获取包的物理路径
                        String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                        // 以文件的方式扫描整个包下的文件 并添加到集合中
                        findClassesByFile(packagePath, filePath, classes);
                    } else if ("jar".equals(protocol)) {
                        // 如果是jar包文件
                        // 获取jar
                        JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        //扫描jar包文件 并添加到集合中
                        findClassesByJar(packagePath, jar, classes);
                    }
                }
            }
        } catch (IOException e) {
            throw new JingException(e, "failed to get class [packagePath: {}]", packagePath);
        }

        return classes;
    }

    public static <T> Set<Class<? super T>> getClassByPackageAndInterface(String packagePath, T interfaceType) throws JingException {
        //第一个class类的集合
        Set<Class<?>> classes = getClassByPackage(packagePath);
        Set<Class<? super T>> retSet = new HashSet<>();
        for (Class<?> clazz : classes) {
            if (Arrays.asList(clazz.getGenericInterfaces()).contains(JService.class)) {
                retSet.add((Class<? super T>) clazz);
            }
        }
        return retSet;
    }

    public static boolean checkRelations(Class parent, Class child) {
        return parent == child || child.getGenericSuperclass() == parent || Arrays.asList(child.getGenericInterfaces()).contains(parent);
    }

    public static ArrayList<Field> getDeclaredFieldsByType(Class clazz, Class type) {
        Field[] fields = clazz.getDeclaredFields();
        ArrayList<Field> fieldList = new ArrayList<>();
        int size = GenericUtil.countArray(fields);
        Field field;
        for (int i$ = 0; i$ < size; i$++) {
            field = fields[i$];
            if (checkRelations(type, field.getType())) {
                fieldList.add(field);
            }
        }
        return fieldList;
    }

    public static <T> T createInstance(Class<T> clazz) throws JingException {
        try {
            return clazz.newInstance();
        }
        catch (Exception e) {
            throw new JingException(e, "failed to create instance for class [{}]", clazz.getName());
        }
    }

    public static <T> T createInstance(Class<T> clazz, Pair2<Class<?>, Object>... parameters) throws JingException {
        try {
            int count = null == parameters ? 0 : parameters.length;
            if (0 == count) {
                return clazz.newInstance();
            }
            else {
                Class<?>[] parameterTypes = new Class[count];
                Object[] parameterArr = new Object[count];
                for (int i$ = 0; i$ < count; i$++) {
                    parameterTypes[i$] = parameters[i$].getA();
                    parameterArr[i$] = parameters[i$].getB();
                }
                Constructor<T> constructor = clazz.getConstructor(parameterTypes);
                return constructor.newInstance(parameterArr);
            }
        }
        catch (Exception e) {
            throw new JingException(e, "failed to create instance for class [{}]", clazz.getName());
        }
    }

    public static boolean isPrimitive(Object object) {
        try {
            return ((Class<?>) object.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isFieldStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    public static boolean isMethodStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    public static void setDeclaredFieldByForce(Object object, String name, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(name);
            if (isFieldStatic(field)) {
                throw new JingException("wrong static field");
            }
            field.setAccessible(true);
            field.set(object, value);
        }
        catch (Exception e) {
            throw new JingRuntimeException(e, "failed to set the value [{}] of [{}]", name, object.getClass().getSimpleName());
        }
    }

    public static void setFieldByForce(Object object, String name, Object value) {
        try {
            Field field = object.getClass().getField(name);
            if (isFieldStatic(field)) {
                throw new JingException("wrong static field");
            }
            field.setAccessible(true);
            field.set(object, value);
        }
        catch (Exception e) {
            throw new JingRuntimeException(e, "failed to set the value [{}] of [{}]", name, object.getClass().getSimpleName());
        }
    }

    public static Object getDeclaredFieldByForce(Object object, String name) {
        try {
            Field field = object.getClass().getDeclaredField(name);
            if (isFieldStatic(field)) {
                throw new JingException("wrong static field");
            }
            field.setAccessible(true);
            return field.get(object);
        }
        catch (Exception e) {
            throw new JingRuntimeException(e, "failed to get the value [{}] of [{}]", name, object.getClass().getSimpleName());
        }
    }

    public static Object getFieldByForce(Object object, String name) {
        try {
            Field field = object.getClass().getField(name);
            if (isFieldStatic(field)) {
                throw new JingException("wrong static field");
            }
            field.setAccessible(true);
            return field.get(object);
        }
        catch (Exception e) {
            throw new JingRuntimeException(e, "failed to get the value [{}] of [{}]", name, object.getClass().getSimpleName());
        }
    }

    public static void setStaticFieldByForce(Class clazz, String name, Object value) {
        try {
            Field field = clazz.getDeclaredField(name);
            if (!isFieldStatic(field)) {
                throw new JingException("not static field");
            }
            field.setAccessible(true);
            field.set(null, value);
        }
        catch (Exception e) {
            throw new JingRuntimeException(e, "failed to set the value [{}] of [{}]", name, clazz.getSimpleName());
        }
    }

    public static Object executeMethod(Object object, MethodInformation methodInformation) {
        try {
            Method method = object.getClass().getMethod(methodInformation.getMethodName(), methodInformation.getMethodTypes());
            if (isMethodStatic(method)) {
                throw new JingException("wrong static method");
            }
            method.setAccessible(true);
            methodInformation.setResult(method.invoke(object, methodInformation.getMethodValues()));
            return methodInformation.isVoid() ? null : methodInformation.getResult();
        }
        catch (Throwable t) {
            throw new JingRuntimeException(t, "failed to execute method {} of {}", methodInformation, object.getClass().getSimpleName());
        }
    }

    public static Object executeDeclaredMethod(Object object, MethodInformation methodInformation) {
        try {
            Method method = object.getClass().getDeclaredMethod(methodInformation.getMethodName(), methodInformation.getMethodTypes());
            if (isMethodStatic(method)) {
                throw new JingException("wrong static method");
            }
            method.setAccessible(true);
            methodInformation.setResult(method.invoke(object, methodInformation.getMethodValues()));
            return methodInformation.isVoid() ? null : methodInformation.getResult();
        }
        catch (Throwable t) {
            throw new JingRuntimeException(t, "failed to execute method {} of {}", methodInformation, object.getClass().getSimpleName());
        }
    }

    public static Object executeStaticMethod(Class clazz, MethodInformation methodInformation) {
        try {
            Method method = clazz.getDeclaredMethod(methodInformation.getMethodName(), methodInformation.getMethodTypes());
            if (isMethodStatic(method)) {
                throw new JingException("wrong static method");
            }
            method.setAccessible(true);
            methodInformation.setResult(method.invoke(clazz, methodInformation.getMethodValues()));
            return methodInformation.isVoid() ? null : methodInformation.getResult();
        }
        catch (Throwable t) {
            throw new JingRuntimeException(t, "failed to execute method {} of {}", methodInformation, clazz.getSimpleName());
        }
    }
}