package com.reliefzk.middleware.dp.pipeline.valve;

import com.reliefzk.middleware.dp.pipeline.PipelineInvocationHandle;
import com.reliefzk.middleware.dp.pipeline.Condition;
import com.reliefzk.middleware.dp.pipeline.PipelineContext;

/**
 * 当条件满足时，执行循环体。
 *
 * @author kui.zhouk
 * @version $Id: AbstractValve.java, v 0.1 2017年09月28日 21:19 kui.zhouk Exp $
 */
public class WhileLoopValve extends LoopValve {
    /**
     * 条件
     */
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public void invoke(PipelineContext pipelineContext) throws Exception {
        PipelineInvocationHandle handle = initLoop(pipelineContext);
        while (condition.isSatisfied(pipelineContext)) {
            invokeBody(handle);
            if (handle.isBroken()) {
                break;
            }
        }
        pipelineContext.invokeNext();
    }
}
