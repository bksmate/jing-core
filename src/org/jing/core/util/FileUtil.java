package org.jing.core.util;

import org.jing.core.lang.Configuration;
import org.jing.core.lang.Const;
import org.jing.core.lang.JingException;
import org.jing.core.lang.Pair2;
import org.jing.core.logger.JingLogger;
import org.jing.core.logger.sys.SingleLogger;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-01-09 <br>
 */
@SuppressWarnings({ "WeakerAccess", "unused", "ResultOfMethodCallIgnored", "Duplicates" }) public class FileUtil {
    /**
     * LOGGER. <br>
     */
    private static volatile InnerLogger LOGGER = new InnerLogger();

    @SuppressWarnings("SameParameterValue") static class InnerLogger {
        JingLogger logger = null;

        void createLogger() {
            if (null == logger && Configuration.hasInit()) {
                logger = JingLogger.getLogger(FileUtil.class);
            }
        }

        boolean checkLogger() {
            return null != logger/* && JingLogger.hasInit()*/;
        }

        void info(String msg, String parameters) {
            createLogger();
            if (checkLogger()) {
                logger.info(msg, parameters);
            }
            else {
                SingleLogger.log(msg, parameters);
            }
        }

        void error(String msg, Throwable t, Object... parameters) {
            createLogger();
            if (checkLogger()) {
                logger.error(msg, t, parameters);
            }
            else {
                SingleLogger.err(msg, parameters);
                SingleLogger.err(StringUtil.getErrorStack(t));
            }
        }

        void error(String msg, Throwable t) {
            createLogger();
            if (checkLogger()) {
                logger.error(msg, t);
            }
            else {
                SingleLogger.err(msg);
                SingleLogger.err(StringUtil.getErrorStack(t));
            }
        }
    }

    public static BufferedReader getBufferedReader(File file, String encoding) throws JingException {
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
        }
        catch (Exception e) {
            throw new JingException(e, "failed to get bufferedReader [filePath: {}]", file.getAbsolutePath());
        }
    }

    public static String readFile(File file) throws JingException {
        return readFile(file, null);
    }

    public static String readFile(File file, String encoding) throws JingException {
        String filePath = file.getAbsolutePath();
        LOGGER.info("Try To Read File [filePath: {}]", filePath);
        String retString;
        try {
            encoding = StringUtil.ifEmpty(encoding, "utf-8");
            BufferedReader br = getBufferedReader(file, encoding);
            StringBuilder stbr = new StringBuilder();
            String row = br.readLine();
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
            throw new JingException(e, "failed To Read File [filePath: {}]", filePath);
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
        LOGGER.info("Try To Read File [filePath: {}]", filePath);
        String retString = null;
        try {
            encoding = StringUtil.ifEmpty(encoding, "utf-8");
            BufferedReader br = getBufferedReader(file, encoding);
            String row;
            boolean initFlag = false;
            while (null != (row = br.readLine())) {
                if (null == retList) {
                    retList = new ArrayList<>();
                }
                retList.add(row);
            }
            br.close();
        }
        catch (Exception e) {
            throw new JingException(e, "failed To Read File [filePath: {}]", filePath);
        }
        return retList;
    }

    public static void zip(String zipFilePath, String srcFilePath) throws JingException {
        zip(zipFilePath, srcFilePath, false);
    }

    public static void zip(String zipFilePath, String srcFilePath, boolean skipParentDirectory) throws JingException {
        File zipFile = new File(zipFilePath);
        if (zipFile.exists()) {
            zipFile.delete();
        }
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile))) {
            File sourceFile = new File(srcFilePath);
            if (skipParentDirectory && !sourceFile.isDirectory()) {
                throw new JingException("src file must be directory");
            }
            String base = sourceFile.getName();
            if (skipParentDirectory) {
                File[] fileList = sourceFile.listFiles();
                int count = GenericUtil.countArray(fileList);
                for (int i$ = 0; i$ < count; i$++) {
                    assert fileList != null;
                    zipSubFunc(out, fileList[i$], fileList[i$].getName());
                }
            }
            else {
                zipSubFunc(out, sourceFile, base);
            }
        }
        catch (JingException e) {
            throw e;
        }
        catch (Throwable t) {
            throw new JingException(t, t.getMessage());
        }
    }

    private static void zipSubFunc(ZipOutputStream out, File srcFile, String base) throws Exception {
        if (srcFile.isDirectory()) {
            File[] fileList = srcFile.listFiles();
            int count = GenericUtil.countArray(fileList);
            if (count == 0) {
                out.putNextEntry(new ZipEntry(base + File.separator));
            }
            else {
                for (int i$ = 0; i$ < count; i$++) {
                    assert fileList != null;
                    zipSubFunc(out, fileList[i$], base + File.separator + fileList[i$].getName());
                }
            }
        }
        else {
            out.putNextEntry(new ZipEntry(base));
            try (
                FileInputStream fos = new FileInputStream(srcFile);
                BufferedInputStream bis = new BufferedInputStream(fos)
            ) {
                int tag;
                while ((tag = bis.read()) != -1) {
                    out.write(tag);
                }
            }
        }
    }

    public static ArrayList<ZipEntry> unzipFile(ZipFile zipFile) {
        ArrayList<ZipEntry> retList = new ArrayList<>();
        Enumeration<? extends ZipEntry> zipFiles = zipFile.entries();
        ZipEntry zipEntry;
        while (zipFiles.hasMoreElements()) {
            zipEntry = zipFiles.nextElement();
            retList.add(zipEntry);
        }
        return retList;
    }

    public static boolean copyFileByChannel(File srcFile, File destFile) throws JingException {
        try (
            FileChannel srcChannel =  new FileInputStream(srcFile).getChannel();
            FileChannel destChannel = new FileOutputStream(destFile).getChannel()
        ) {
            destChannel.transferFrom(srcChannel, 0, srcChannel.size());
            return true;
        }
        catch (Exception e) {
            throw new JingException(e, e.getMessage());
        }
    }

    public static boolean copyFileByChannel(String srcFilePath, String destFilePath) throws JingException {
        return copyFileByChannel(new File(srcFilePath), new File(destFilePath));
    }

    public static boolean copyFileByStream(File srcFile, File destFile, int bufferSize) throws JingException {
        try (
            InputStream in = new FileInputStream(srcFile);
            OutputStream out = new FileOutputStream(destFile)
        ) {
            byte[] buffer = new byte[bufferSize];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            return true;
        }
        catch (Exception e) {
            throw new JingException(e, e.getMessage());
        }
    }

    public static boolean copyFileByStream(File srcFile, File destFile) throws JingException {
        return copyFileByStream(srcFile, destFile, 1024);
    }

    public static boolean copyFileByStream(String srcFilePath, String destFilePath) throws JingException {
        return copyFileByStream(new File(srcFilePath), new File(destFilePath), 1024);
    }

    public static boolean copyFileByStream(String srcFilePath, String destFilePath, int bufferSize) throws JingException {
        return copyFileByStream(new File(srcFilePath), new File(destFilePath), bufferSize);
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

    public static boolean mkdirs(String directoryPath) {
        return mkdirs(new File(directoryPath));
    }

    public static boolean mkdirs(File directory) {
        return !((!directory.exists() || !directory.isDirectory()) && !directory.mkdirs());
    }

    public static String readResource(String filePath, boolean log) throws JingException {
        try {
            ClassLoader[] classLoader = ClassUtil.getClassLoader(FileUtil.class);
            int size = GenericUtil.countArray(classLoader);
            for (int i$ = 0; i$ < size; i$++) {
                ClassLoader classLoader$ = classLoader[i$];
                InputStream is;
                if (null != classLoader$) {
                    is = classLoader$.getResourceAsStream(filePath);
                    if (null == is) {
                        is = classLoader$.getResourceAsStream(Const.SYSTEM_FILE_SEPARATOR + filePath);
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
                            int index = is.read(bytes$, off$, len$);
                            stbr$.append(new String(bytes$, 0, index));
                        }
                        is.close();
                        return stbr$.toString();
                    }
                }
            }
        }
        catch (Exception e) {
            throw new JingException(e, "failed To Read Properties [filePath: {}]", filePath);
        }
        return "";
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
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), encoding))) {
            File parent = file.getParentFile();
            if (null == parent || (parent.exists() && parent.isDirectory()) || parent.mkdirs()) {
                writer.write(content);
                writer.flush();
            }
            else {
                throw new JingException("failed to mkdirs");
            }
        }
        catch (Exception e) {
            LOGGER.error("Failed to append file {}", e, file.getAbsolutePath());
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
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), encoding))) {
            File parent = file.getParentFile();
            if (null == parent || (parent.exists() && parent.isDirectory()) || parent.mkdirs()) {
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
                throw new JingException("failed to mkdirs");
            }
        }
        catch (Exception e) {
            LOGGER.error("failed to append file {}", e, file.getAbsolutePath());
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
        if (path.contains(":") || path.startsWith("\\") || path.startsWith("/")) {
            return path;
        }
        else {
            path = Configuration.getJingHome() + path;
            return path;
        }
    }

    /**
     * Description: 根据文件大小获得最适合得表达式. <br>
     * @param fileSize 文件大小 <br>
     * @return &lt;单位(0->b, 1->kb, 2->mb, 3->gb, 4->tb), 大小&gt;
     */
    public static Pair2<Integer, Float> getFileSizeString(long fileSize) {
        float rest = fileSize, temp;
        int index = 0;
        while (1 <= (temp = rest / 1024)) {
            index ++;
            rest = temp;
            if (index == 4) {
                break;
            }
        }
        return new Pair2<>(index, rest);
    }

    public static String getGeneralFileSizeString(long fileSize) {
        String[] units = {"b", "kb", "mb", "gb", "tb"};
        Pair2<Integer, Float> res = getFileSizeString(fileSize);
        return String.format("%.2f", res.getB()) + " " + units[res.getA()];
    }

    public static String getFileSuffix(File file) {
        return getFileSuffix(file.getName());
    }

    public static String getFileSuffix(String fileName) {
        int potIndex = fileName.lastIndexOf(".");
        if (-1 != potIndex) {
            return fileName.substring(potIndex + 1);
        }
        return "";
    }
}
