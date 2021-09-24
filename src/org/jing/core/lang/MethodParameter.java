package org.jing.core.lang;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-24 <br>
 */
@SuppressWarnings({ "WeakerAccess", "unused" }) public class MethodParameter {
    private Class type;

    private Object value;

    public MethodParameter(Class type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
