package com.reliefzk.middleware.pipeline;

/**
 * 代表一个条件
 * @author kui.zhouk
 */
public interface Condition {

    /**
     * 如满足条件，则返回<code>true</code>
     * @param pipelineContext
     * @return
     */
    boolean isSatisfied(PipelineContext pipelineContext);
}