package com.reliefzk.middleware.pipeline.valve;


import com.reliefzk.middleware.pipeline.Pipeline;
import com.reliefzk.middleware.pipeline.PipelineContext;
import com.reliefzk.middleware.pipeline.PipelineInvocationHandle;

/**
 * 根据数据列表进行循环执行
 * @author kui.zhouk
 */
public abstract class AbstDataListLoopValve<T> extends AbstractValve {

    private final static String DEFAULT_LOOP_DATA_NAME = "__loopData__";
    private final static String DEFAULT_LOOP_DATA_LIST_NAME = "__loopDataList__";
    /* 循环执行的单次时间名称 */
    private String loopDataName;
    /* 循环执行数据集名称 */
    private String loopDataListName;

    /* 执行pipeline */
    protected Pipeline loopBody;

    /**
     * 初始化body调用句柄
     * @param pipelineContext
     * @return
     */
    protected PipelineInvocationHandle initLoop(PipelineContext pipelineContext) {
        PipelineInvocationHandle handle = getLoopBody().newInvocation(pipelineContext);
        return handle;
    }

    public Pipeline getLoopBody() {
        return loopBody;
    }

    public String getLoopDataListName() {
        return (String) defaultIfNull(loopDataListName, DEFAULT_LOOP_DATA_LIST_NAME);
    }

    public void setLoopDataListName(String loopDataListName) {
        this.loopDataListName = loopDataListName;
    }

    public void setLoopBody(Pipeline loopBody) {
        this.loopBody = loopBody;
    }

    public String getLoopDataName() {
        return (String)defaultIfNull(loopDataName, DEFAULT_LOOP_DATA_NAME);
    }

    public void setLoopDataName(String loopDataName) {
        this.loopDataName = defaultIfNull(loopDataName, DEFAULT_LOOP_DATA_LIST_NAME);
    }
}