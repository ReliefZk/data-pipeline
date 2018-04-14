package com.reliefzk.middleware.pipeline;

import java.util.Map;

/**
 * 是由pipeline提供给valve的一个上下文对象，它代表了当前pipeline的执行状态，并控制pipeline的执行步骤。
 *
 * @author kui.zhouk
 */
public interface PipelineContext extends PipelineState {

    /**
     * 执行pipeline中下一个valve。
     *
     * @throws IllegalStateException 假如该方法被多次调用。
     */
    void invokeNext() throws PipelineException;


    /**
     * 设置返回值
     * @param body
     * @param <T>
     */
    <T> void setResponseBody(T body);


    /**
     * 获取请求参数
     * @param <T>
     * @return
     */
    <T> T getRequestBody();

    /**
     * 设置父pipeline的状态
     * @param key
     * @param value
     */
    void setParentContextAttribute(String key, Object value);

    /**
     * 清空参数
     * @param key
     */
    void clearAttribute(String key);

    Map<String, Object> attributes();
}