package org.jing.core.service;

import org.jing.core.lang.JingException;
import org.jing.core.lang.itf.JService;
import org.jing.core.logger.JingLogger;
import org.jing.core.util.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-05-25 <br>
 */
@SuppressWarnings("unused")
public class Services {
    private static final JingLogger LOGGER = JingLogger.getLogger(Services.class);

    public static void reloadServiceMapping() throws JingException {
        new ServiceInit().init(ServiceInit.getParameters());
    }

    public static Object callService(String serviceCode, Object param) throws JingException {
        if (StringUtil.isEmpty(serviceCode)) {
            throw new JingException("serviceCode is empty");
        }
        // 1. 找映射类
        Class<? super JService> tempJService = ServiceInit.mappingService(serviceCode);
        if (null == tempJService) {
            throw new JingException("cannot find service: " + serviceCode);
        }
        Object retObject = null;
        try {
            JService service = (JService) tempJService.newInstance();
            // 2. 找类里是否有带了serviceCode的注解的方法.
            // yes: 直接调用遍历搜索到的第一个方法并返回.
            // no: 使用默认的execute方法.
            Method[] methods = service.getClass().getDeclaredMethods();
            ServiceCode serviceCode$;
            String[] serviceCodes$;
            boolean findFlag = false;
            for (Method m$ : methods) {
                serviceCode$ = m$.getAnnotation(ServiceCode.class);
                if (null != serviceCode$) {
                    serviceCodes$ = serviceCode$.value();
                    for (String s : serviceCodes$) {
                        if (serviceCode.equalsIgnoreCase(s)) {
                            // yes: 直接调用遍历搜索到的第一个方法并返回.
                            m$.setAccessible(true);
                            retObject = m$.invoke(service, param);
                            findFlag = true;
                        }
                    }
                }
            }
            if (!findFlag) {
                // no: 使用默认的execute方法.
                retObject = service.execute(param);
            }
        }
        catch (InvocationTargetException e) {
            if (null != e.getTargetException() && e.getTargetException() instanceof JingException) {
                throw (JingException) e.getTargetException();
            }
            else {
                throw new JingException(e);
            }
        }
        catch (JingException e) {
            throw e;
        }
        catch (Throwable t) {
            throw new JingException(t);
        }
        return retObject;
    }
}
