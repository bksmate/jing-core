package org.jing.core.lang;

import org.jing.core.lang.itf.JEnum;
import org.jing.core.util.GenericUtil;

import java.lang.reflect.Method;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-22 <br>
 */
public class EnumFactory {
    public static <T> T mapEnum(Class<T> enumType, Object... input) throws JingException {
        if (!enumType.isEnum()) {
            throw new JingException("input type {} must be enum", enumType.getSimpleName());
        }
        if (!JEnum.class.isAssignableFrom(enumType)) {
            throw new JingException("type {} must implement JEnum", enumType.getSimpleName());
        }
        try {
            final Method valuesMethod = enumType.getMethod("values");
            valuesMethod.setAccessible(true);
            @SuppressWarnings("unchecked") T[] values = (T[]) valuesMethod.invoke(null);
            int size = GenericUtil.countArray(values);
            for (int i$ = 0; i$ < size; i$++) {
                if (((JEnum) values[i$]).equalsByType(input)) {
                    return values[i$];
                }
            }
            return null;
        }
        catch (Throwable t) {
            throw new JingException(t);
        }
    }
}
