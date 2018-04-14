package com.reliefzk.middleware.condition;


import com.reliefzk.middleware.pipeline.Condition;

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