package com.reliefzk.middleware.dp.condition;


import com.reliefzk.middleware.dp.pipeline.Condition;

/**
 * 组合式的condition基类
 * @author kui.zhouk
 */
public abstract class AbstractCompositeCondition extends AbstractCondition {

    private static Condition[] EMPTY_CONDITIONS = new Condition[0];
    private Condition[] conditions;

    public Condition[] getConditions() {
        return conditions == null ? EMPTY_CONDITIONS : conditions;
    }

    public void setConditions(Condition[] conditions) {
        this.conditions = conditions;
    }

    protected String getDesc() {
        return AbstractCompositeCondition.class.getSimpleName();
    }

}