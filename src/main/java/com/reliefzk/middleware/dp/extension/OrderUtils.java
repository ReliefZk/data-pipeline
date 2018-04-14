package com.reliefzk.middleware.dp.extension;


import com.google.common.base.Preconditions;
import com.reliefzk.middleware.dp.pipeline.Valve;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author kui.zhouk
 * @version $Id: OrderUtils.java, v 0.1 2018年01月29日 10:16 kui.zhouk Exp $
 */
public class OrderUtils {

    /**
     * 重排序
     * @param valves
     * @return
     */
    public static <T extends Valve> T[] shuffleOrder(T[] valves){
        Preconditions.checkState(ArrayUtils.isNotEmpty(valves), "Pipeline.Valves shoud not null");
        List<T> valveList = Arrays.asList(valves);

        /* 排序 */
        Collections.sort(valveList, new Comparator<T>() {
            @Override
            public int compare(Valve v1, Valve v2) {
                return v1.order() - v2.order();
            }
        });
        return (T[]) valveList.toArray();
    }

}