package org.jing.core.lang;

import java.lang.reflect.Field;

/**
 * Description: 继承了该底层DTO的对象将会被重写以下方法: <br>
 *  1. toString <br>
 *
 * @author: bks <br>
 * @createDate: 2019-09-17 <br>
 */
public abstract class BaseDto {
    @Override
    public String toString() {
        StringBuilder stbr = new StringBuilder("[");
        stbr.append(getClass().getSimpleName()).append(": ");
        Field[] fields = this.getClass().getDeclaredFields();
        boolean flag = false;
        for (Field f$ : fields) {
            if (flag) {
                stbr.append(", ");
            }
            f$.setAccessible(true);
            stbr.append(f$.getName()).append("=");
            try {
                stbr.append(f$.get(this));
            }
            catch (Exception e) {
                stbr.append("null");
            }
            flag = true;
        }
        stbr.append("]");
        return stbr.toString();
    }
}
