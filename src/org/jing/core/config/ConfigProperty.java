package org.jing.core.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-23 <br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ConfigProperty {
    String path();
    boolean required() default false;
    String defaultValue() default "";
}
