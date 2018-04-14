package com.reliefzk.middleware.dp.condition;


import com.reliefzk.middleware.dp.pipeline.Condition;
import com.reliefzk.middleware.dp.pipeline.PipelineContext;

/**
 * 只有当下属的所有condition均返回<code>true</code>时，才返回<code>true</code>。
 *
 * @author kui.zhouk
 */
public class AllOf extends AbstractCompositeCondition {

    public boolean isSatisfied(PipelineContext pipelineContext) {
        for (Condition condition : getConditions()) {
            if (!condition.isSatisfied(pipelineContext)) {
                return false;
            }
        }

        return true;
    }

}
