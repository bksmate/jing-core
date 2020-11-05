package org.jing.core.ext;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-11-05 <br>
 */
@SuppressWarnings("SameParameterValue")
public class JClassLoader {
    private static volatile JClassLoader instance = null;

    private Libraries libraries;

    private Applications applications;

    public static JClassLoader getInstance() {
        if (null == instance) {
            synchronized (JClassLoader.class) {
                if (null == instance) {
                    instance = new JClassLoader();
                }
            }
        }
        return instance;
    }

    private JClassLoader() {
        libraries = new Libraries();
        applications = new Applications(libraries);
    }

    private static void throwException(boolean condition, String message) throws Exception {
        if (condition) {
            throw new Exception(message);
        }
    }

    private static void setValue(Object object, String fieldName, Object value) throws Exception {
        Field parentField = null;
        Class className = object.getClass();
        while (true) {
            try {
                parentField = className.getDeclaredField(fieldName);
                break;
            }
            catch (Exception ignored) {}
            className = className.getSuperclass();
            if (className == Object.class) {
                break;
            }
        }
        throwException(null == parentField, "Failed to get field: " + fieldName + " in class: " + object.getClass().getName());
        boolean accessible = parentField.isAccessible();
        if (!accessible) {
            parentField.setAccessible(true);
        }
        try {
            parentField.set(object, value);
            parentField.setAccessible(accessible);
        }
        catch (IllegalAccessException e) {
            parentField.setAccessible(accessible);
            throw new Exception("Failed to set value with field: " + fieldName + " in class: " + object.getClass().getName());
        }
    }

    public JClassLoader addLibraryByJarFilePath(String name, String jarFilePath) throws Exception {
        applications.updateRelations(libraries.addLibraryByJarFile(name, new File(jarFilePath)).updateRelations());
        return this;
    }

    public JClassLoader addLibraryByJarFile(String name, File jarFile) throws Exception {
        applications.updateRelations(libraries.addLibraryByJarFile(name, jarFile).updateRelations());
        return this;
    }

    public JClassLoader removeLibraryByName(String name) throws Exception {
        applications.updateRelations(libraries.removeLibraryByName(name).updateRelations());
        return this;
    }

    public boolean checkLibraryExistsByName(String name) {
        return libraries.checkLibraryExistsByName(name);
    }

    public JClassLoader registerApplication(String className, String jarFilePath) throws Exception {
        applications.registerApplicationByJarFilePath(className, jarFilePath);
        return this;
    }

    public JClassLoader registerApplication(String className, File jarFile) throws Exception {
        applications.registerApplicationByJarFile(className, jarFile);
        return this;
    }

    public boolean checkApplicationExists(String className) {
        return applications.checkApplicationExists(className);
    }

    public JClassLoader removeApplication(String className) throws Exception {
        applications.removeApplication(className);
        return this;
    }

    public int getApplicationRunningNums(String className) throws Exception {
        return applications.getApplicationRunningNums(className);
    }

    public JClassLoader addRunningSubApplication(String className, Object subApp) throws Exception {
        applications.addRunningSubApplication(className, subApp);
        return this;
    }

    public Object getRunningSubApplications(String className) throws Exception {
        return applications.getRunningSubApplications(className);
    }

    public JClassLoader removeRunningSubApplication(String className, Object subApp) throws Exception {
        applications.removeRunningSubApplication(className, subApp);
        return this;
    }

    public static class SingleClassLoader extends URLClassLoader {
        private String name;
        
        private File jarFile;

        private JarURLConnection cachedJarFiles;

        private static boolean canCloseJar = false;
        
        private boolean initFlag = false;

        static {
            // 1.7之后可以直接调用close方法关闭打开的jar，需要判断当前运行的环境是否支持close方法，如果不支持，需要缓存，避免卸载模块后无法删除jar
            try {
                URLClassLoader.class.getMethod("close");
                canCloseJar = true;
            } catch (Exception ignored) {}
        }

        private SingleClassLoader(String name) {
            super(new URL[]{});
            this.name = name;
        }

        private SingleClassLoader(String name, ClassLoader parent) {
            super(new URL[]{}, parent);
            this.name = name;
        }
        
        private void validate() throws Exception {
            throwException(initFlag, "Cannot change SingleClassLoader");
        }

        /*private void setJarFile(String filePath) throws Exception {
            setJarFile(new File(filePath));
        }*/

        private void setJarFile(File jarFile) throws Exception {
            validate();
            JClassLoader.throwException(jarFile == null, "Null jar file");
            JClassLoader.throwException(!jarFile.exists(), "Jar file doesn't exists: " + jarFile.getAbsolutePath());
            JClassLoader.throwException(!jarFile.isFile(), "Jar file is directory: " + jarFile.getAbsolutePath());
            this.jarFile = jarFile;
            URL jarFileURL = jarFile.toURI().toURL();
            addURL(jarFileURL);
        }

        @Override
        protected void addURL(URL url) {
            if (!canCloseJar) {
                try {
                    URLConnection uc = url.openConnection();
                    if (uc instanceof JarURLConnection) {
                        cachedJarFiles = (JarURLConnection) uc;
                        cachedJarFiles.setUseCaches(true);
                        cachedJarFiles.getManifest();
                    }
                } catch (Exception ignored) {}
            }
            super.addURL(url);
        }

        public void close() throws IOException {
            if (canCloseJar) {
                super.close();
                initFlag = false;
            } else {
                cachedJarFiles.getJarFile().close();
                initFlag = false;
            }
        }

        @Override
        public String toString() {
            return String.format("<%s: %s>", name, jarFile.getName());
        }

        public String getName() {
            return name;
        }

        public String getJarFilePath() {
            return jarFile.getAbsolutePath();
        }
    }

    private static class Libraries {
        private final ArrayList<SingleClassLoader> classLoaderList = new ArrayList<SingleClassLoader>();

        Libraries() {
        }

        private Libraries addLibraryByJarFilePath(String name, String jarFilePath) throws Exception {
            addLibraryByJarFile(name, new File(jarFilePath));
            return this;
        }

        private Libraries addLibraryByJarFile(String name, File jarFile) throws Exception {
            JClassLoader.throwException(null == name || name.length() == 0, "Empty jar file sign");
            for (SingleClassLoader loader : classLoaderList) {
                JClassLoader.throwException(loader.name.equalsIgnoreCase(name), "Duplicate jar file with sign: " + name);
            }
            SingleClassLoader classLoader = new SingleClassLoader(name);
            classLoader.setJarFile(jarFile);
            classLoaderList.add(classLoader);
            return this;
        }

        private Libraries updateRelations() throws Exception {
            int count = classLoaderList.size();
            if (count != 0) {
                JClassLoader.setValue(classLoaderList.get(0), "parent", ClassLoader.getSystemClassLoader());
            }
            for (int i$ = 1; i$ < count; i$++) {
                JClassLoader.setValue(classLoaderList.get(i$), "parent", classLoaderList.get(i$ - 1));
            }

            return this;
        }

        private boolean checkLibraryExistsByName(String name) {
            for (SingleClassLoader loader : classLoaderList) {
                if (loader.name.equalsIgnoreCase(name)) {
                    return true;
                }
            }
            return false;
        }

        private Libraries removeLibraryByName(String name) throws Exception {
            SingleClassLoader loader;
            for (int i$ = 0, count = classLoaderList.size(); i$ < count; i$++) {
                loader = classLoaderList.get(i$);
                if (loader.name.equalsIgnoreCase(name)) {
                    loader.close();
                    classLoaderList.remove(i$);
                    updateRelations();
                    break;
                }
            }
            return this;
        }
        
        private SingleClassLoader getLastClassLoader() {
            return classLoaderList.get(classLoaderList.size() - 1);
        }
    }

    private static class Applications {
        private final HashMap<String, Thread> appMap = new HashMap<>();

        private final HashMap<String, ArrayList<Object>> subAppMap = new HashMap<>();

        private final ArrayList<SingleClassLoader> classLoaderList = new ArrayList<SingleClassLoader>();
        
        private Libraries libraries;

        Applications(Libraries libraries) {
            this.libraries = libraries;
        }

        private void registerApplicationByJarFilePath(String className, String jarFilePath) throws Exception {
            registerApplicationByJarFile(className, new File(jarFilePath));
        }

        private void registerApplicationByJarFile(String className, File jarFile) throws Exception {
            JClassLoader.throwException(null == className || className.length() == 0, "Empty hot application class");
            for (SingleClassLoader loader : classLoaderList) {
                JClassLoader.throwException(loader.name.equalsIgnoreCase(className), "Duplicate hot application class: " + className);
            }
            SingleClassLoader classLoader = new SingleClassLoader(className, libraries.getLastClassLoader());
            classLoader.setJarFile(jarFile);
            classLoaderList.add(classLoader);
            JHotApplication application = (JHotApplication) Class.forName(className, true, classLoader).newInstance();
            Thread appThread = new Thread(application);
            appMap.put(className, appThread);
            appThread.start();
        }

        private void updateRelations(Libraries libraries) throws Exception {
            ClassLoader parent = libraries.classLoaderList.get(libraries.classLoaderList.size() - 1);
            for (SingleClassLoader loader : classLoaderList) {
                JClassLoader.setValue(loader, "parent", parent);
            }
        }

        private boolean checkApplicationExists(String className) {
            if (null == className) {
                return false;
            }
            for (SingleClassLoader loader : classLoaderList) {
                if (loader.name.equalsIgnoreCase(className)) {
                    return true;
                }
            }
            return false;
        }

        private void removeApplication(String className) throws Exception {
            throwException(0 < getApplicationRunningNums(className), "Application " + className + " is still running");
            String name = className;
            SingleClassLoader loader;
            for (int i$ = 0, count = classLoaderList.size(); i$ < count; i$++) {
                loader = classLoaderList.get(i$);
                if (loader.name.equalsIgnoreCase(name)) {
                    loader.close();
                    classLoaderList.remove(i$);
                    subAppMap.remove(name);
                    appMap.get(name).interrupt();
                    appMap.remove(name);
                    break;
                }
            }
        }

        private int getApplicationRunningNums(String className) throws Exception {
            if (null == className) {
                return -1;
            }
            ArrayList<Object> list = subAppMap.get(className);
            if (null == list) {
                return -1;
            }
            else {
                return list.size();
            }
        }

        private void addRunningSubApplication(String className, Object subApp) throws Exception {
            throwException(!checkApplicationExists(className), "application[" + className + "] doesn't register");
            ArrayList<Object> list = subAppMap.get(className);
            if (null == list) {
                list = new ArrayList<Object>();
                subAppMap.put(className, list);
            }
            list.add(subApp);
        }

        private Object getRunningSubApplications(String className) throws Exception {
            throwException(!checkApplicationExists(className), "application[" + className + "] doesn't register");
            return subAppMap.get(className);
        }

        private void removeRunningSubApplication(String className, Object subApp) throws Exception {
            throwException(!checkApplicationExists(className), "application[" + className + "] doesn't register");
            if (0 < getApplicationRunningNums(className)) {
                subAppMap.get(className).remove(subApp);
            }
        }
    }
}
