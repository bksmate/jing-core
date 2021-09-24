package test;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.ForceList;

import java.lang.Exception;
import java.util.Arrays;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-23 <br>
 */
public class Demo4ForceList {
    private Demo4ForceList() throws Exception {
        int[] arr = new int[10];
        int[] arr$ = new int[15];
        for (int i$ = 0; i$ < 15; i$++) {
            arr$[i$] = i$;
        }
        arr$ = Arrays.copyOf(arr$, 20);
        System.out.println(Arrays.toString(arr$));
        System.arraycopy(arr, 1, arr$, 4, 4);
        System.out.println(Arrays.toString(arr$));
        arr$ = Arrays.copyOfRange(arr$, 2, 5);
        System.out.println(Arrays.toString(arr$));

        Carrier[] cArr = new Carrier[5];
        cArr[0] = new Carrier();
        System.out.println(Arrays.toString(cArr));
        cArr = Arrays.copyOf(cArr, 10);
        System.out.println(Arrays.toString(cArr));
    }

    public static void main(String[] args) throws Exception {
        new Demo4ForceList();
    }
}
