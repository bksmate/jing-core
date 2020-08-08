package org.jing.core.lang;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        stbr.append(this.getClass().getSimpleName()).append(": ");
        Class clazz = this.getClass();
        List<Field> fields = new ArrayList<Field>();
        while (clazz != null){
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        boolean flag = false;
        for (Field field: fields) {
            if (flag) {
                stbr.append(", ");
            }
            else {
                flag = true;
            }
            field.setAccessible(true);
            stbr.append(field.getName()).append("=");
            try {
                stbr.append(field.get(this));
            } catch (Exception e) {
                stbr.append("null");
            }
        }
        stbr.append("]");
        return stbr.toString();
    }
}
