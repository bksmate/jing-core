package org.jing.core.lang;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-24 <br>
 */
@Deprecated
public class JingList<E> extends ArrayList<E> {
    private static Method method$grow;
    static {
        try {
            method$grow = ArrayList.class.getDeclaredMethod("grow", Integer.class);
            method$grow.setAccessible(true);
        }
        catch (Throwable t) {
            method$grow = null;
            t.printStackTrace();
        }
    }

    @Override public boolean add(E e) {
        return super.add(e);
    }

    @Override public E set(int index, E element) {
        if (index < 0) {
            throw new JingRuntimeException("invalid index: {}", index);
        }
        if (index > size()) {
            try {
                method$grow.invoke(this, index);
            }
            catch (Throwable t) {
                throw new JingRuntimeException(t);
            }
        }
        return super.set(index, element);
    }
}
