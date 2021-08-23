package org.jing.core.service;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.lang.itf.JInit;
import org.jing.core.lang.itf.JService;
import org.jing.core.logger.JingLogger;
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
@SuppressWarnings({ "WeakerAccess", "unused", "unchecked" }) public class ServiceInit implements JInit {
    private static JingLogger LOGGER = JingLogger.getLogger(ServiceInit.class);

    private static Carrier parameters = null;

    private static HashMap<String, Class<? super JService>> serviceMap = new HashMap<>();

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
        String path = FileUtil.buildPathWithHome(parameters.getStringByName("path", ""));
        addMapperByFilePath(path);
    }

    public void addMapperByFilePath(String filePath) throws JingException {
        Carrier carrier = Carrier.parseXML(FileUtil.readFile(filePath, "UTF-8"));
        int count = carrier.getCount("service");
        List<Carrier> mapperList = new ArrayList<>();
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
                if (Integer.parseInt(aCarrier.getStringByName("index", "0")) > Integer.parseInt(bCarrier.getStringByName("index", "0"))) {
                    mapperList.set(k$, bCarrier);
                    mapperList.set(j$, aCarrier);
                    k$ = j$;
                }
            }
        }
        List<Class<? super Class<JService>>> classList = new ArrayList<>();
        for (Carrier mapper : mapperList) {
            classList.addAll(ClassUtil.getClassByPackageAndInterface(mapper.getStringByName("path", ""), JService.class));
        }

        operate(classList);
    }

    public void addMapperByPackagePath(String pkgPath) throws JingException {
        List<Class<? super Class<JService>>> classList = new ArrayList<>(
            ClassUtil.getClassByPackageAndInterface(pkgPath, JService.class));
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
                            throw new JingException("duplicate path mapping configuration: " + serviceCode$);
                        }
                        else {
                            try {
                                LOGGER.imp("Load mapper: {}", clazz.getName());
                                serviceMap.put(serviceCode$, (Class<? super JService>) clazz);
                            }
                            catch (Exception e) {
                                throw new JingException(e, "invalid implements: " + clazz.getName());
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
            throw new JingException("failed to get service mapping: " + serviceCode);
        }
        else {
            LOGGER.debug("get service mapping: [serviceCode: {}][serviceImpl: {}]", serviceCode, tempJService.getName());
        }
        return tempJService;
    }
}
