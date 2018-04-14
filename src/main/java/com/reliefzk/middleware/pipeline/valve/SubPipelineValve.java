package com.reliefzk.middleware.pipeline.valve;

import com.reliefzk.middleware.pipeline.Pipeline;
import com.reliefzk.middleware.pipeline.PipelineContext;

/**
 * 用来执行一个子pipeline
 *     注意：这个pipeline里的pipeline context是独立的
 * @author kui.zhouk
 */
public class SubPipelineValve extends AbstractValve {

    private Pipeline subPipeline;

    public Pipeline getSubPipeline() {
        return subPipeline;
    }

    public void setSubPipeline(Pipeline subPipeline) {
        this.subPipeline = subPipeline;
    }

    public void invoke(PipelineContext pipelineContext) throws Exception {
        subPipeline.newInvocation(pipelineContext).invoke();
        pipelineContext.invokeNext();


    }

}