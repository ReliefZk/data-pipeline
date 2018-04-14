/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.reliefzk.middleware.dp.extension;


import com.google.common.base.Stopwatch;
import com.reliefzk.middleware.dp.pipeline.*;
import com.reliefzk.middleware.dp.pipeline.valve.ConditionValve;

import java.util.concurrent.TimeUnit;

/**
 * 对<code>Pipeline</code>的扩展实现。
 * @author kui.zhouk
 */
public class ConditionPipelineImpl implements Pipeline {

    /**
     * 阈值列表 执行先后顺序根据装配顺序
     */
    private ConditionValve[] valves;
    private String           label;

    public ConditionValve[] getValves() {
        return valves;
    }

    public void setValves(ConditionValve[] valves) {
        this.valves = OrderUtils.shuffleOrder(valves);
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public PipelineInvocationHandle newInvocation() {
        return new ConditionPipelineImpl.PipelineContextImpl(null);
    }

    public PipelineInvocationHandle newInvocation(PipelineContext parentContext) {
        return new ConditionPipelineImpl.PipelineContextImpl(parentContext);
    }

    /**
     * 实现<code>PipelineContext</code>
     */
    protected final class PipelineContextImpl extends AbstPipelineContext {

        public PipelineContextImpl(){
            super(null);
        }

        public PipelineContextImpl(PipelineContext parentContext) {
            super(parentContext);
        }

        public PipelineInvocationHandle newInvocation() {
            return new ConditionPipelineImpl.PipelineContextImpl();
        }

        public PipelineInvocationHandle newInvocation(PipelineContext parentContext) {
            return new ConditionPipelineImpl.PipelineContextImpl(parentContext);
        }

        /**
         * 框架不处理任何异常， 请各valve实现者自己处理异常，否则会影响后续valve
         * @throws PipelineException
         */
        @Override
        public void invokeNext() throws PipelineException {
            if (isBroken()) {
                return;
            }
            try {
                executingIndex++;
                executedIndex++;
                if (executingIndex < valves.length) {
                    ConditionValve valve = valves[executingIndex];
                    Stopwatch stopwatch = Stopwatch.createStarted();
                    try {
                        if(valve.isSatisfied(this)) {
                            valve.invoke(this);
                        } else {
                            invokeNext();
                        }
                    } catch (Exception e) {
                        throw new PipelineException("Failed to invoke" + valve, e);
                    } finally {
                        /* 添加耗时日志 */
                        long elapsedMill = stopwatch.elapsed(TimeUnit.MILLISECONDS);
                    }
                }
            } finally {
                executingIndex--;
            }
        }

        public Valve[] getValves(){
            return valves;
        }
    }

}