package test;

import org.jing.core.lang.GlobalContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-24 <br>
 */
public class Demo4Context {
    public static void main(String[] args) throws Throwable {
        try (GlobalContext globalContext = GlobalContext.port()) {
            TempGlobalContext context = GlobalContext.map(TempGlobalContext.class);
            context.setInputStream(new FileInputStream(new File("temp/temp.xml")));
            InputStream inputStream = context.getInputStream();
            context.setOutputStream(new FileOutputStream(new File("temp/temp.xml")));
            OutputStream outputStream = context.getOutputStream();
            System.out.println();
        }
    }
}
