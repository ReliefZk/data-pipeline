package com.reliefzk.middleware.pipeline;

/**
 * 代码执行顺序，比喻为一个管道
 *
 * <code>Valve</code>可以决定是否继续执行后续的valves，或是中断整个pipeline的执行。
 * @author kui.zhouk
 */
public interface Pipeline {

    /**
     * pipeline标签(name)
     *
     * @return
     */
    String getLabel();

    /**
     * 创建一次新的执行
     * @return
     */
    PipelineInvocationHandle newInvocation();

    /**
     * 创建一次新的执行，并将此次执行看作另一个执行的子过程
     * @param parentContext
     * @return
     */
    PipelineInvocationHandle newInvocation(PipelineContext parentContext);

}