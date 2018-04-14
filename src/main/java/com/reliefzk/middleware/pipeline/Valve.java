package com.reliefzk.middleware.pipeline;

/**
 * 表示管道上的"阈值"
 *
 * @author kui.zhouk
 */
public interface Valve {
    /**
     * 调用当前
     * @param pipelineContext
     * @throws Exception
     */
    public void invoke(PipelineContext pipelineContext) throws Exception;

    public int order();
}