package com.reliefzk.middleware.pipeline;

/**
 * 代表一次pipeline的执行
 *
 * @author kui.zhouk
 */
public interface PipelineInvocationHandle extends PipelineState {
    /**
     * 执行pipeline。
     *
     * @throws IllegalStateException 被中断的pipeline无法再次invoke，否则抛此异常。
     */
    void invoke() throws PipelineException;
}