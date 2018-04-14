package com.reliefzk.middleware.dp.pipeline.valve;


import com.reliefzk.middleware.dp.pipeline.PipelineContext;
import com.reliefzk.middleware.dp.pipeline.PipelineInvocationHandle;
import com.reliefzk.middleware.dp.pipeline.Pipeline;

/**
 * 支持try-catch-finally结构。
 *
 * @author kui.zhouk
 * @version $Id: AbstractValve.java, v 0.1 2017年09月28日 21:19 kui.zhouk Exp $
 */
public class TryCatchFinallyValve extends AbstractValve {

    private final static String DEFAULT_EXCEPTION_NAME = "_pipeline__exception__";

    private Pipeline tryPipeline;
    private Pipeline catchPipeline;
    private Pipeline finallyPipeline;
    private String   exceptionName;


    public void invoke(PipelineContext pipelineContext) throws Exception {
        try {
            if (tryPipeline != null) {
                tryPipeline.newInvocation(pipelineContext).invoke();
            }
        } catch (Exception e) {
            if (catchPipeline != null) {
                PipelineInvocationHandle handle = catchPipeline.newInvocation(pipelineContext);
                handle.setAttribute(getExceptionName(), e);
                handle.invoke();
            } else {
                throw e;
            }
        } finally {
            if (finallyPipeline != null) {
                finallyPipeline.newInvocation(pipelineContext).invoke();
            }
        }

        pipelineContext.invokeNext();
    }

    public Pipeline getTryPipeline() {
        return tryPipeline;
    }

    public void setTryPipeline(Pipeline tryPipeline) {
        this.tryPipeline = tryPipeline;
    }

    public Pipeline getCatchPipeline() {
        return catchPipeline;
    }

    public void setCatchPipeline(Pipeline catchPipeline) {
        this.catchPipeline = catchPipeline;
    }

    public Pipeline getFinallyPipeline() {
        return finallyPipeline;
    }

    public void setFinallyPipeline(Pipeline finallyPipeline) {
        this.finallyPipeline = finallyPipeline;
    }

    public String getExceptionName() {
        return (String)defaultIfNull(exceptionName, DEFAULT_EXCEPTION_NAME);
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }
}
