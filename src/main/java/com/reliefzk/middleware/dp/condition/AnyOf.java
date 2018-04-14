package com.reliefzk.middleware.dp.condition;


import com.reliefzk.middleware.dp.pipeline.Condition;
import com.reliefzk.middleware.dp.pipeline.PipelineContext;

/**
 * 当下属的任何一个condition返回<code>true</code>时，就返回<code>true</code>。
 *
 * @author kui.zhouk
 */
public class AnyOf extends AbstractCompositeCondition {

    public boolean isSatisfied(PipelineContext pipelineContext) {
        for (Condition condition : getConditions()) {
            if (condition.isSatisfied(pipelineContext)) {
                return true;
            }
        }

        return false;
    }
}
