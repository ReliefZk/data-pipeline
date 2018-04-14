package com.reliefzk.middleware.pipeline;

/**
 * Pipeline的当前状态，是被
 *  <code>PipelineContext</code>和
 *  <code>PipelineInvocationHandle</code>
 * 共享的接口。
 *
 * @author kui.zhouk
 */
public interface PipelineState {

    /**
     * 检查pipeline将是否被中断
     * @return
     */
    boolean isBroken();

    /**
     * 检查pipeline将是否已执行完成
     * @return
     */
    boolean isFinished();

    /**
     * 阻断
     * @return
     */
    void doBreak();

    /**
     * 取得当前pipeline执行的状态。
     * <p>
     * 假如取不到，则向上查找，直到找到或者到达顶层。
     * </p>
     */
    Object getAttribute(String key);

    /**
     * 设置当前pipeline的状态。
     * <p>
     * 设置当前pipeline执行的状态，会覆盖上层同名的状态值，然而却不会影响上一层执行的状态。
     * 当执行返回到上一层时，所以有的改变都被丢弃。
     * </p>
     */
    void setAttribute(String key, Object value);

    /**
     * 设置请求参数
     * @param body
     * @param <T>
     */
    <T> void setRequestBody(T body);


    /**
     * 获取返回值
     * @param <T>
     * @return
     */
    <T> T getResponseBody();
}