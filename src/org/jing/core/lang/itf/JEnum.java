package org.jing.core.lang.itf;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-22 <br>
 */
public interface JEnum<T> {
    boolean equalsByType(Object... input);

    T output();

    Class<T> getOutputType();
}
