package test;

import org.jing.core.lang.annotation.Getter;
import org.jing.core.lang.annotation.Setter;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-24 <br>
 */
public interface TempGlobalContext {
    @Setter("INPUT")
    void setInputStream(InputStream inputStream);

    @Getter("INPUT")
    InputStream getInputStream();

    @Setter("OUTPUT")
    void setOutputStream(OutputStream outputStream);

    @Getter("OUTPUT")
    OutputStream getOutputStream();
}
