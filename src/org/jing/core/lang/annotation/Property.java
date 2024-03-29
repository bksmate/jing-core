package org.jing.core.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-01-31 <br>
 */
@SuppressWarnings("unused")
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface Property {
    String value();
}
