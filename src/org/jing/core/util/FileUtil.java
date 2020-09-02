package org.jing.core.util;

import org.jing.core.lang.Configuration;
import org.jing.core.lang.Const;
import org.jing.core.lang.ExceptionHandler;
import org.jing.core.lang.JingException;
import org.jing.core.logger.JingLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-01-09 <br>
 */
public class FileUtil {
    /**
     * logger. <br>
     */
    private static volatile InnerLogger logger = new InnerLogger();

    static class InnerLogger {
        JingLogger logger = null;

        void createLogger() {
            if (null == logger && Configuration.hasInit()) {
                logger = JingLogger.getLogger(FileUtil.class);
            }
        }

        boolean checkLogger() {
            return null != logger && JingLogger.hasInit();
        }

        void info(String msg, String parameters) {
            createLogger();
            if (checkLogger()) {
                logger.info(msg, parameters);
            }
            else {
                System.out.println(StringUtil.mixParameters(msg, parameters));
            }
        }

        void error(String msg, Throwable t, String... parameters) {
            createLogger();
            if (checkLogger()) {
                logger.error(msg, t, parameters);
            }
            else {
                System.out.println(StringUtil.mixParameters(msg, parameters));
                t.printStackTrace();
            }
        }

        void error(String msg, Throwable t) {
            createLogger();
            if (checkLogger()) {
                logger.error(msg, t);
            }
            else {
                System.out.println(msg);
                t.printStackTrace();
            }
        }
    }

    public static BufferedReader getBufferedReader(File file, String encoding) throws JingException {
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
        }
        catch (Exception e) {
            ExceptionHandler.publish("UTIL-FILE-00005",
                new StringBuilder("Failed to get BufferedReader [filePath: ").append(file.getAbsolutePath()).append("]").toString(), e);
            return null;
        }
    }

    public static String readFile(File file) throws JingException {
        return readFile(file, null);
    }

    public static String readFile(File file, String encoding) throws JingException {
        String filePath = file.getAbsolutePath();
        logger.info("Try To Read File [filePath: {}]", filePath);
        String retString = null;
        try {
            encoding = StringUtil.ifEmpty(encoding, "utf-8");
            BufferedReader br = getBufferedReader(file, encoding);
            StringBuilder stbr = new StringBuilder();
            String row = br.readLine();
            if (!StringUtil.isEmpty(row)) {
                retString = "";
            }
            boolean initFlag = false;
            while (null != row) {
                if (initFlag) {
                    stbr.append("\n");
                }
                stbr.append(row);
                row = br.readLine();
                initFlag = true;
            }
            br.close();
            retString = stbr.toString();
        }
        catch (Exception e) {
            ExceptionHandler.publish("UTIL-FILE-00003",
                new StringBuilder("Failed To Read File [filePath: ").append(filePath).append("]").toString(), e);
        }
        return retString;
    }

    public static String readFile(String filePath, String encoding) throws JingException {
        return readFile(new File(filePath), encoding);
    }

    public static String readFile(String filePath) throws JingException {
        return readFile(new File(filePath), null);
    }

    public static List<String> readFile2List(String filePath) throws JingException {
        return readFile2List(new File(filePath), null);
    }

    public static List<String> readFile2List(File file) throws JingException {
        return readFile2List(file, null);
    }

    public static List<String> readFile2List(String filePath, String encoding) throws JingException {
        return readFile2List(new File(filePath), encoding);
    }

    public static List<String> readFile2List(File file, String encoding) throws JingException {
        List<String> retList = null;
        String filePath = file.getAbsolutePath();
        logger.info("Try To Read File [filePath: {}]", filePath);
        String retString = null;
        try {
            encoding = StringUtil.ifEmpty(encoding, "utf-8");
            BufferedReader br = getBufferedReader(file, encoding);
            String row;
            boolean initFlag = false;
            while (null != (row = br.readLine())) {
                if (null == retList) {
                    retList = new ArrayList<String>();
                }
                retList.add(row);
                initFlag = true;
            }
            br.close();
        }
        catch (Exception e) {
            ExceptionHandler.publish("UTIL-FILE-00003",
                new StringBuilder("Failed To Read File [filePath: ").append(filePath).append("]").toString(), e);
        }
        return retList;
    }

    public static void zip(String zipFilePath, String srcFilePath) throws Exception {
        File zipFile = new File(zipFilePath);
        if (zipFile.exists()) {
            zipFile.delete();
        }
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
        BufferedOutputStream bos = new BufferedOutputStream(out);
        File sourceFile = new File(srcFilePath);
        zipSubFunc(out, bos, sourceFile, sourceFile.getName());
        bos.close();
        out.close();
    }

    public static void zipSubFunc(ZipOutputStream out, BufferedOutputStream bos, File srcFile, String base)
        throws Exception {
        if (srcFile.isDirectory()) {
            File[] flist = srcFile.listFiles();
            if (flist.length == 0) {
                System.out.println(base + "/");
                out.putNextEntry(new ZipEntry(base + "/"));
            }
            else {
                for (int i = 0; i < flist.length; i++) {
                    zipSubFunc(out, bos, flist[i], base + "/" + flist[i].getName());
                }
            }
        }
        else {
            File file = new File(base);
            out.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fos = new FileInputStream(srcFile);
            BufferedInputStream bis = new BufferedInputStream(fos);
            int tag;
            while ((tag = bis.read()) != -1) {
                out.write(tag);
            }
            bis.close();
            fos.close();

        }
    }

    public static void deleteFile(File file) {
        if (null == file || !file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            int size = null == files ? 0 : files.length;
            for (int i$ = 0; i$ < size; i$++) {
                deleteFile(files[i$]);
            }
            file.delete();
        }
        else {
            file.delete();
        }
    }

    public static void deleteFile(String filePath) {
        deleteFile(new File(filePath));
    }

    public static String readResource(String filePath, boolean log) throws JingException {
        /*if (log(log)) {
            logger.info(new StringBuilder("Try To Read File [filePath: ").append(filePath).append("]").toString());
        }*/
        try {
            ClassLoader[] classLoader = ClassUtil.getClassLoader(FileUtil.class);
            int size = classLoader.length;
            for (int i$ = 0; i$ < size; i$++) {
                ClassLoader classLoader$ = classLoader[i$];
                InputStream is = null;
                if (null != classLoader$) {
                    is = classLoader$.getResourceAsStream(filePath);
                    if (null == is) {
                        is = classLoader$.getResourceAsStream(
                            new StringBuilder(Const.SYSTEM_FILE_SEPARATOR).append(filePath).toString());
                    }
                    if (null != is) {
                        StringBuilder stbr$ = new StringBuilder();
                        int maxLength$ = is.available();
                        int size$ = maxLength$ / Const.SYSTEM_MAX_BYTES_SIZE + 1;
                        for (int j$ = 0; j$ < size$; j$++) {
                            int off$ = j$ * Const.SYSTEM_MAX_BYTES_SIZE;
                            int len$ = (j$ + 1) * Const.SYSTEM_MAX_BYTES_SIZE > maxLength$
                                ? maxLength$
                                : Const.SYSTEM_MAX_BYTES_SIZE;
                            byte[] bytes$ = new byte[len$];
                            is.read(bytes$, off$, len$);
                            stbr$.append(new String(bytes$));
                        }
                        return stbr$.toString();
                    }
                }
            }
        }
        catch (Exception e) {
            ExceptionHandler.publish("UTIL-FILE-00004",
                new StringBuilder("Failed To Read Properties [filePath: ").append(filePath).append("]").toString(), e);
        }
        return null;
    }

    public static void writeFile(File file, String content, String encoding) {
        writeFile(file, content, false, encoding);
    }

    public static void writeFile(File file, String content) {
        writeFile(file, content, false, null);
    }

    public static void writeFile(File file, String content, boolean append) {
        writeFile(file, content, append, null);
    }

    public static void writeFile(File file, String content, boolean append, String encoding) {
        encoding = StringUtil.ifEmpty(encoding, "utf-8");
        BufferedWriter writer = null;
        try {
            File parent = file.getParentFile();
            if ((parent.exists() && parent.isDirectory()) || parent.mkdirs()) {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), encoding));
                writer.write(content);
                writer.flush();
            }
            else {
                ExceptionHandler.publish("Failed to mkdirs");
            }
        }
        catch (Exception e) {
            logger.error("Failed to write file {}", e, file.getAbsolutePath());
        }
        finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (Exception e) {
                    logger.error("Failed to close OutputStreamWriter", e);
                }
                finally {
                    writer = null;
                }
            }
        }
    }

    public static void writeFile(String filePath, String content, boolean append) {
        writeFile(new File(filePath), content, append);
    }

    public static void writeFile(String filePath, String content, String encoding) {
        writeFile(new File(filePath), content, encoding);
    }

    public static void writeFile(String filePath, String content) {
        writeFile(new File(filePath), content);
    }

    public static void writeFile(String filePath, String content, boolean append, String encoding) {
        writeFile(new File(filePath), content, append, encoding);
    }

    public static void writeFile(File file, List<String> rowList, String encoding) {
        writeFile(file, rowList, false, encoding);
    }

    public static void writeFile(File file, List<String> rowList) {
        writeFile(file, rowList, false, null);
    }

    public static void writeFile(File file, List<String> rowList, boolean append) {
        writeFile(file, rowList, append, null);
    }

    public static void writeFile(File file, List<String> rowList, boolean append, String encoding) {
        encoding = StringUtil.ifEmpty(encoding, "utf-8");
        BufferedWriter writer = null;
        try {
            File parent = file.getParentFile();
            if ((parent.exists() && parent.isDirectory()) || parent.mkdirs()) {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), encoding));
                int count = GenericUtil.countList(rowList);
                for (int i$ = 0; i$ < count; i$++) {
                    if (0 != i$) {
                        writer.newLine();
                    }
                    writer.write(rowList.get(i$));
                }
                writer.flush();
            }
            else {
                ExceptionHandler.publish("Failed to mkdirs");
            }
        }
        catch (Exception e) {
            logger.error("Failed to write file {}", e, file.getAbsolutePath());
        }
        finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (Exception e) {
                    logger.error("Failed to close OutputStreamWriter", e);
                }
                finally {
                    writer = null;
                }
            }
        }
    }

    public static void writeFile(String filePath, List<String> rowList, boolean append) {
        writeFile(new File(filePath), rowList, append);
    }

    public static void writeFile(String filePath, List<String> rowList, String encoding) {
        writeFile(new File(filePath), rowList, encoding);
    }

    public static void writeFile(String filePath, List<String> rowList) {
        writeFile(new File(filePath), rowList);
    }

    public static void writeFile(String filePath, List<String> rowList, boolean append, String encoding) {
        writeFile(new File(filePath), rowList, append, encoding);
    }

    /**
     *  构建适合当前OS的路径字符串, 入参里的路径分隔符应该是?. <br>
     *
     * @param path <br>
     * @return <br>
     */
    public static String buildPath(String path) {
        String separator = System.getProperty("file.separator");
        int index;
        while ((index = path.indexOf("?")) != -1) {
            path = path.substring(0, index) + separator + path.substring(index + 1);
        }
        return path;
    }

    /**
     *  构建适合当前OS的路径字符串, 入参里的路径分隔符应该是?. <br>
     *
     * @param path <br>
     * @return <br>
     */
    public static String buildPathWithHome(String path) {
        String separator = System.getProperty("file.separator");
        int index;
        while ((index = path.indexOf("?")) != -1) {
            path = path.substring(0, index) + separator + path.substring(index + 1);
        }
        path = Configuration.getJingHome() + path;
        return path;
    }
}
