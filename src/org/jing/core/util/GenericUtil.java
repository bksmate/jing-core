package org.jing.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.jing.core.lang.ExceptionHandler;
import org.jing.core.lang.JingException;
import org.jing.core.lang.JingExtraException;

public class GenericUtil {

    /**
     * Description: String Mapping. <br>
     * If key doesn't exist in map, return default String. <br>
     * If need case sensitive, set ignoreCase as false. <br>
     * It may return a null String, plz be careful. <br>
     *
     * @author bks <br>
     * @param map <br>
     * @param key <br>
     * @param defaultString <br>
     * @param ignoreCase <br>
     * @return <br>
     */
    public static String stringMapping(HashMap<String, String> map, String key, String defaultString, boolean ignoreCase) {
        String retString = null;
        boolean findFlag = false;
        if (null != map && null != key) {
            if (!ignoreCase) {
                if (map.containsKey(key)) {
                    retString = map.get(key);
                    findFlag = true;
                }
            }
            else {
                for (Entry<String, String> entry : map.entrySet()) {
                    if (key.equalsIgnoreCase(entry.getKey())) {
                        retString = entry.getValue();
                        findFlag = true;
                        break;
                    }
                }
            }
        }
        if (!findFlag) {
            retString = defaultString;
        }
        return retString;
    }

    /**
     * Description: Initialize A HashMap With Object Array. <br>
     * Two of array must be the same length. If not, return a empty HashMap. <br>
     * The return HashMap won't be null, at least return a empty HashMap. <br>
     *
     * @author bks <br>
     * @param <A> <br>
     * @param <B> <br>
     * @param key <br>
     * @param value <br>
     * @return <br>
     */
    public static <A, B> HashMap<A, B> initHashMap(A[] key, B[] value) {
        HashMap<A, B> retMap = new HashMap<A, B>();
        if (null != key && null != value) {
            if (key.length == value.length) {
                int size = key.length;
                for (int i = 0; i < size; i++) {
                    retMap.put(key[i], value[i]);
                }
            }
        }
        return retMap;
    }

    /**
     * Description: Initialize A HashMap With One Pair. <br>
     * The return HashMap won't be null, at least return a empty HashMap. <br>
     *
     * @author bks <br>
     * @param <A> <br>
     * @param <B> <br>
     * @param key <br>
     * @param value <br>
     * @return <br>
     */
    public static <A, B> HashMap<A, B> initHashMap(A key, B value) {
        HashMap<A, B> retMap = new HashMap<A, B>();
        retMap.put(key, value);
        return retMap;
    }

    /**
     * Description: Initialize An List With Array. <br>
     *
     * @author bks <br>
     * @param <E> <br>
     * @param elements <br>
     * @return <br>
     */
    public static <E> List<E> initList(E[] elements) {
        ArrayList<E> retList = new ArrayList<E>();
        if (null != elements) {
            int size = elements.length;
            Collections.addAll(retList, elements);
        }
        return retList;
    }

    /**
     * Description: Get The First Value By Key. <br>
     * It may return a null String, plz be careful. <br>
     *
     * @author bks <br>
     * @param list <br>
     * @param key <br>
     * @return <br>
     */
    public static String getFirst(List<HashMap<String, String>> list, String key) {
        String retStr = null;
        int count = null == list ? 0 : list.size();
        for (int i = 0; i < count; i++) {
            HashMap<String, String> tempMap = list.get(i);
            if (tempMap.containsKey(key)) {
                retStr = tempMap.get(key);
                break;
            }
        }
        return retStr;
    }

    public static boolean isEmpty(List<?> list) {
        return null == list || list.size() == 0;
    }

    public static int countList(List<?> list) {
        return null == list ? 0 : list.size();
    }

    public static <T> int countArray(T[] array) {
        return null == array ? 0 : array.length;
    }
}