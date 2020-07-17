package test;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.Configuration;
import org.jing.core.lang.ExceptionHandler;
import org.jing.core.lang.JingException;
import org.jing.core.lang.itf.JService;
import org.jing.core.service.ServiceInit;
import org.jing.core.util.ClassUtil;

import java.lang.Exception;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Set;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-06-29 <br>
 */
public class Demo4JService {
    private Demo4JService() throws Exception {
        /*Set<Class<?>> set = ClassUtil.getClassByPackage("test");
        for (Class<?> clazz : set) {
            Type[] typeArr = clazz.getGenericInterfaces();
            Class[] clazzArr = clazz.getInterfaces();
            System.out.println(clazz.getName() + "|" + Arrays.asList(typeArr).contains(JService.class));
        }*/
        Set<Class<? super Class<JService>>> set = ClassUtil.getClassByPackageAndInterface("test", JService.class);
        System.out.println(set);
        Configuration.getInstance();
        ServiceInit.mappingService("test");
        Carrier carrier = new Carrier();
        carrier.asXML();
    }

    public static void main(String[] args) throws Exception {
        new Demo4JService();
    }
}
