package com.reliefzk.middleware.pipeline.valve;


import com.reliefzk.middleware.pipeline.Pipeline;
import com.reliefzk.middleware.pipeline.PipelineContext;
import com.reliefzk.middleware.pipeline.PipelineInvocationHandle;

/**
 * 用来反复执行同一个子pipeline。
 *
 * @author kui.zhouk
 * @version $Id: AbstractValve.java, v 0.1 2017年09月28日 21:19 kui.zhouk Exp $
 */
public class LoopValve extends AbstractValve {

    private final static int    DEFAULT_MAX_LOOP          = 0;
    private final static String DEFAULT_LOOP_COUNTER_NAME = "__loopCount__"; //loopindex

    private Pipeline loopBody;
    private Integer  maxLoopCount;
    private String   loopCounterName;

    public Pipeline getLoopBody() {
        return loopBody;
    }

    public void setLoopBody(Pipeline loopBody) {
        this.loopBody = loopBody;
    }

    public int getMaxLoopCount() {
        return maxLoopCount == null ? DEFAULT_MAX_LOOP : maxLoopCount;
    }

    public void setMaxLoopCount(int maxLoopCount) {
        this.maxLoopCount = maxLoopCount <= 0 ? 0 : maxLoopCount;
    }

    public String getLoopCounterName() {
        return (String)defaultIfNull(loopCounterName, DEFAULT_LOOP_COUNTER_NAME);
    }

    public void setLoopCounterName(String loopCounterName) {
        this.loopCounterName = defaultIfNull(loopCounterName, DEFAULT_LOOP_COUNTER_NAME);
    }

    public void invoke(PipelineContext pipelineContext) throws Exception {
        PipelineInvocationHandle handle = initLoop(pipelineContext);
        for(int index = 0; index < getMaxLoopCount() && !handle.isBroken(); index++){
            invokeBody(handle);
        }
        pipelineContext.invokeNext();
    }

    protected PipelineInvocationHandle initLoop(PipelineContext pipelineContext) {
        PipelineInvocationHandle handle = getLoopBody().newInvocation(pipelineContext);
        handle.setAttribute(getLoopCounterName(), 0);
        return handle;
    }

    protected void invokeBody(PipelineInvocationHandle handle) {
        String loopCounterName = getLoopCounterName();
        int loopCount = (Integer) handle.getAttribute(loopCounterName);
        handle.invoke();
        handle.setAttribute(loopCounterName, ++loopCount);
    }

}
