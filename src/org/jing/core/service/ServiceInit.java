package org.jing.core.service;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.ExceptionHandler;
import org.jing.core.lang.JingException;
import org.jing.core.lang.annotation.ServiceCode;
import org.jing.core.lang.itf.JInit;
import org.jing.core.lang.itf.JService;
import org.jing.core.logger.JingLogger;
import org.jing.core.util.CarrierUtil;
import org.jing.core.util.ClassUtil;
import org.jing.core.util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2019-03-21 <br>
 */
public class ServiceInit implements JInit {
    private static JingLogger LOGGER = JingLogger.getLogger(ServiceInit.class);

    private static Carrier parameters = null;

    private static HashMap<String, Class<? super JService>> serviceMap = new HashMap<String, Class<? super JService>>();

    public static Carrier getParameters() {
        return parameters;
    }

    @Override
    public void init(Carrier params) throws JingException {
        parameters = params;
        if (null == parameters) {
            LOGGER.imp("Empty parameter for JService Initialize");
            return;
        }
        String path = FileUtil.buildPathWithHome(parameters.getString("path", ""));
        addMapperByFilePath(path);
    }

    public void addMapperByFilePath(String filePath) throws JingException {
        Carrier carrier = CarrierUtil.string2Carrier(FileUtil.readFile(filePath, "UTF-8"));
        int count = carrier.getCount("service");
        List<Carrier> mapperList = new ArrayList<Carrier>();
        for (int i$ = 0; i$ < count; i$++) {
            mapperList.add(carrier.getCarrier("service", i$));
        }
        int size = mapperList.size();
        // sort
        Carrier aCarrier;
        Carrier bCarrier;
        for (int i$ = 0, k$; i$ < size - 1; i$++) {
            aCarrier = mapperList.get(i$);
            k$ = i$;
            for (int j$ = i$ + 1; j$ < size; j$++) {
                bCarrier = mapperList.get(j$);
                if (Integer.parseInt(aCarrier.getString("index", "0")) > Integer.parseInt(bCarrier.getString("index", "0"))) {
                    mapperList.set(k$, bCarrier);
                    mapperList.set(j$, aCarrier);
                    k$ = j$;
                }
            }
        }
        List<Class<? super Class<JService>>> classList = new ArrayList<Class<? super Class<JService>>>();
        for (Carrier mapper : mapperList) {
            classList.addAll(ClassUtil.getClassByPackageAndInterface(mapper.getString("path", ""), JService.class));
        }

        operate(classList);
    }

    public void addMapperByPackagePath(String pkgPath) throws JingException {
        List<Class<? super Class<JService>>> classList = new ArrayList<Class<? super Class<JService>>>(ClassUtil.getClassByPackageAndInterface(pkgPath, JService.class));
        operate(classList);
    }

    private void operate(List<Class<? super Class<JService>>> classList) throws JingException {
        ServiceCode serviceCode;
        for (Class<?> clazz : classList) {
            serviceCode = clazz.getAnnotation(ServiceCode.class);
            if (null != serviceCode) {
                String[] serviceCodes = serviceCode.value();
                for (String serviceCode$ : serviceCodes) {
                    if (null != serviceCode$ && serviceCode$.length() != 0) {
                        if (serviceMap.containsKey(serviceCode$)) {
                            throw new JingException("Duplicate Path Mapping Configuration: " + serviceCode$);
                        }
                        else {
                            try {
                                LOGGER.imp("Load mapper: {}", clazz.getName());
                                serviceMap.put(serviceCode$, (Class<? super JService>) clazz);
                            }
                            catch (Exception e) {
                                throw new JingException(e, "Invalid Implements: " + clazz.getName());
                            }
                        }
                    }
                }
            }
        }
    }

    public static Class<? super JService> mappingService(String serviceCode) throws JingException {
        Class<? super JService> tempJService = serviceMap.get(serviceCode);
        if (null == tempJService) {
            throw new JingException("Failed to get service mapping: " + serviceCode);
        }
        else {
            LOGGER.debug("Get service mapping: [serviceCode: {}][serviceImpl: {}]", serviceCode, tempJService.getName());
        }
        return tempJService;
    }
}
