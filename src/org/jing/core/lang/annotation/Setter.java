package org.jing.core.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-26 <br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Setter {
    String value() default "";
}
