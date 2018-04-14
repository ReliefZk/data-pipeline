package com.reliefzk.middleware.pipeline.valve;


import com.reliefzk.middleware.pipeline.PipelineContext;
import com.reliefzk.middleware.pipeline.PipelineInvocationHandle;

import java.util.List;

/**
 * 根据数据列表进行循环执行
 * @author kui.zhouk
 * @version $Id: DataListLoopValve.java, v 0.1 2017年10月16日 17:12 kui.zhouk Exp $
 */
public class DataListLoopValve<T> extends AbstDataListLoopValve {


    @Override
    public void invoke(PipelineContext pipelineContext) throws Exception {
        /* 根据配置名称获取循环数据集 */
        List<T> dataList = (List<T>)pipelineContext.getAttribute(getLoopDataListName());
        for(T data : dataList){
            PipelineInvocationHandle handle = initLoop(pipelineContext);
            handle.setAttribute(getLoopDataName(), data);
            handle.invoke();
            if(handle.getResponseBody() != null){
                pipelineContext.setResponseBody(handle.getResponseBody());
            }
        }
        pipelineContext.invokeNext();
    }
}