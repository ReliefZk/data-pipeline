package com.reliefzk.middleware.condition;

import com.reliefzk.middleware.pipeline.Condition;
import com.reliefzk.middleware.pipeline.PipelineContext;

/**
 * 当下属的所有conditions均返回<code>false</code>时，才返回<code>true</code>。
 *
 * @author kui.zhouk
 * @version $Id: AbstractCondition.java, v 0.1 2017年09月28日 21:23 kui.zhouk Exp $
 */
public class NoneOf extends AbstractCompositeCondition {

    public boolean isSatisfied(PipelineContext pipelineContext) {
        for (Condition condition : getConditions()) {
            if (condition.isSatisfied(pipelineContext)) {
                return false;
            }
        }
        return true;
    }

}
