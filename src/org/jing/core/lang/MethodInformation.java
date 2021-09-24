package org.jing.core.lang;

import org.jing.core.util.GenericUtil;

import java.util.Arrays;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-24 <br>
 */
@SuppressWarnings({ "WeakerAccess", "UnusedReturnValue", "unused" }) public class MethodInformation {
    private final String methodName;

    private final int size;

    private final MethodParameter[] parameters;

    private final boolean isVoid;

    private Object result;

    public MethodInformation(String methodName, Class returnType, MethodParameter... methodParameters) {
        this.methodName = methodName;
        this.parameters = methodParameters;
        this.size = GenericUtil.countArray(methodParameters);
        this.isVoid = (returnType ==Void.class);
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getMethodTypes() {
        Class<?>[] methodTypes = new Class[size];
        for (int i$ = 0; i$ < size; i$++) {
            methodTypes[i$] = parameters[i$].getType();
        }
        return methodTypes;
    }

    public Object[] getMethodValues() {
        Object[] methodValues = new Object[size];
        for (int i$ = 0; i$ < size; i$++) {
            methodValues[i$] = parameters[i$].getValue();
        }
        return methodValues;
    }

    public Object getResult() {
        if (isVoid) {
            throw new JingRuntimeException("cannot get void");
        }
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isVoid() {
        return isVoid;
    }

    @Override public String toString() {
        return "<name: " + methodName + ", types: " + Arrays.toString(getMethodTypes()) + ">";
    }

    public static void main(String[] args) {
        new MethodInformation("123", Void.class).getResult();
    }
}
