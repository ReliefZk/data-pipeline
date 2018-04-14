package com.reliefzk.middleware.extension;


import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.reliefzk.middleware.pipeline.PipelineContext;
import com.reliefzk.middleware.pipeline.PipelineException;
import com.reliefzk.middleware.pipeline.PipelineInvocationHandle;
import com.reliefzk.middleware.pipeline.Valve;

import java.util.Map;

/**
 * pipeline context 抽象类
 *   子类通用代码
 * @author kui.zhouk
 */
public abstract class AbstPipelineContext  implements PipelineContext, PipelineInvocationHandle {
    private final PipelineContext parentContext;
    private Map<String, Object> attributes;
    private boolean             broken;


    protected int executedIndex  = -1;
    protected int executingIndex = -1;

    public AbstPipelineContext(PipelineContext parentContext) {
        this.parentContext = parentContext;
    }

    @Override
    public boolean isBroken() {
        return broken;
    }

    @Override
    public boolean isFinished() {
        return !isBroken() && executedIndex >= getValves().length;
    }

    protected abstract Valve[] getValves();

    @Override
    public void invoke() throws PipelineException {
        Preconditions.checkState(!isBroken(),"cannot reinvoke a broken pipeline");
        executingIndex = executedIndex = -1;
        invokeNext();
    }

    public Object getAttribute(String key) {
        Object value = null;
        if (attributes != null) {
            value = attributes.get(key);
        }
        if (value == null && parentContext != null) {
            value = parentContext.getAttribute(key);
        }
        return value;
    }

    public void setAttribute(String key, Object value) {
        if (attributes == null) {
            attributes = Maps.newHashMap();

        }
        attributes.put(key, value);
    }

    @Override
    public <T> T getRequestBody() {
        return (T)getAttribute(request_body_key);
    }

    /**
     * 设置父级pipeline的attribute
     * @param key
     * @param value
     */
    @Override
    public void setParentContextAttribute(String key, Object value) {
        Preconditions.checkNotNull(parentContext);
        parentContext.setAttribute(key, value);
    }

    @Override
    public void clearAttribute(String key) {
        if (attributes != null) {
            if(attributes.containsKey(key)){
                attributes.remove(key);
            }
        }
        if(parentContext != null){
            parentContext.clearAttribute(key);
        }
    }

    @Override
    public <T> void setRequestBody(T body) {
        setAttribute(request_body_key, body);
    }

    @Override
    public <T> void setResponseBody(T body){
        setAttribute(response_body_key, body);
        if(parentContext != null && parentContext.getResponseBody() == null){
            parentContext.setResponseBody(body);
        }
    }

    @Override
    public <T> T getResponseBody(){
        return (T)getAttribute(response_body_key);
    }

    @Override
    public void doBreak() {
        this.broken = true;
    }

    public Map<String, Object> attributes(){
        return attributes;
    }

    /* 请求参数 */
    private static final String request_body_key = "__request_body_key__";
    /* 返回值 */
    private static final String response_body_key = "__response_body_key__";
}