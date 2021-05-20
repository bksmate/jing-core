package org.jing.core.service;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.lang.annotation.ServiceCode;
import org.jing.core.lang.itf.JService;
import org.jing.core.logger.JingLogger;
import org.jing.core.logger.JingLoggerConfiguration;
import org.jing.core.util.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-05-25 <br>
 */
@SuppressWarnings("unused") public class ServiceUtil {
    private static final JingLogger LOGGER = JingLogger.getLogger(ServiceUtil.class);

    public static void reloadServiceMapping() throws JingException {
        new ServiceInit().init(ServiceInit.getParameters());
    }

    public static Object callService(String serviceCode, Object param) throws JingException {
        if (StringUtil.isEmpty(serviceCode)) {
            throw new JingException("Service Code is empty");
        }
        LOGGER.debug("[request:{}{}]", JingLoggerConfiguration.getGlobalNewLine(), param instanceof Carrier ? ((Carrier) param).asXML() : param);
        // 1. 找映射类
        Class<? super JService> tempJService = ServiceInit.mappingService(serviceCode);
        if (null == tempJService) {
            throw new JingException("Cannot find Service: " + serviceCode);
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
            throw new JingException(e.getTargetException(), e.getTargetException().getMessage());
        }
        catch (Exception e) {
            throw new JingException(e, e.getMessage());
        }
        LOGGER.debug("[response:{}{}]", JingLoggerConfiguration.getGlobalNewLine(), param instanceof Carrier ? ((Carrier) param).asXML() : param);
        return retObject;
    }
}
