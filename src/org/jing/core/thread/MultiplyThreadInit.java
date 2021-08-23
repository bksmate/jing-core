package org.jing.core.thread;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;
import org.jing.core.lang.itf.JInit;
import org.jing.core.lang.itf.JThread;
import org.jing.core.logger.JingLogger;
import org.jing.core.util.ClassUtil;
import org.jing.core.util.FileUtil;
import org.jing.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-05-27 <br>
 */
@SuppressWarnings("unchecked") public class MultiplyThreadInit implements JInit {
    private static final JingLogger LOGGER = JingLogger.getLogger(MultiplyThreadInit.class);

    private static Carrier parameters;

    @Override
    public void init(Carrier params) throws JingException {
        parameters = params;
        if (null == parameters) {
            LOGGER.imp("Empty parameter for JThread Initialize");
            return;
        }
        Carrier carrier = Carrier.parseXML(
            FileUtil.readFile(FileUtil.buildPathWithHome(parameters.getStringByName("path", "")), "UTF-8")
        );
        int count = carrier.getCount("thread");
        List<Carrier> threadList = new ArrayList<>();
        for (int i$ = 0; i$ < count; i$++) {
            threadList.add(carrier.getCarrier("thread", i$));
        }
        int size = threadList.size();
        // sort
        Carrier aCarrier;
        Carrier bCarrier;
        for (int i$ = 0, k$; i$ < size - 1; i$++) {
            aCarrier = threadList.get(i$);
            k$ = i$;
            for (int j$ = i$ + 1; j$ < size; j$++) {
                bCarrier = threadList.get(j$);
                if (Integer.parseInt(aCarrier.getStringByName("index", "0")) > Integer.parseInt(bCarrier.getStringByName("index", "0"))) {
                    threadList.set(k$, bCarrier);
                    threadList.set(j$, aCarrier);
                    k$ = j$;
                }
            }
        }
        String threadClass;
        Carrier threadParameters;
        for (Carrier threadCarrier : threadList) {
            threadClass = threadCarrier.getStringByName("implements", "");
            if (StringUtil.isNotEmpty(threadClass)) {
                MultiplyThread.createMultiplyThread((Class<? super JThread>) ClassUtil.loadClass(threadClass),
                    threadCarrier.getCount("parameters") > 0 ? threadCarrier.getCarrier("parameters") : null);
            }
        }
    }
}
