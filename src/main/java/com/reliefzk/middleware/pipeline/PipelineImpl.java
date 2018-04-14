package com.reliefzk.middleware.pipeline;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 对<code>Pipeline</code>的实现。
 * @author kui.zhouk
 */
public class PipelineImpl implements Pipeline {

    private static final String DefaultLabel = PipelineImpl.class.getName();

    /**
     * 阈值列表 执行先后顺序根据装配顺序
     */
    private Valve[] valves;
    private String  label;

    public Valve[] getValves() {
        return valves;
    }

    public void setValves(Valve[] valves) {
        this.valves =  valves;
    }

    public String getLabel() {
        return Strings.isNullOrEmpty(label) ? DefaultLabel : label;
    }

    public void setLabel(String label) {
        this.label = Strings.emptyToNull(label);
    }

    public PipelineInvocationHandle newInvocation() {
        return new PipelineContextImpl(null);
    }

    public PipelineInvocationHandle newInvocation(PipelineContext parentContext) {
        return new PipelineContextImpl(parentContext);
    }

    /**
     * 实现<code>PipelineContext</code>
     */
    private final class PipelineContextImpl implements PipelineContext, PipelineInvocationHandle {
        private final PipelineContext parentContext;
        private int executedIndex  = -1;
        private int executingIndex = -1;
        private boolean             broken;
        private Map<String, Object> attributes;

        public PipelineContextImpl(PipelineContext parentContext) {
            this.parentContext = parentContext;
        }

        public PipelineInvocationHandle newInvocation() {
            return new PipelineContextImpl(null);
        }

        public PipelineInvocationHandle newInvocation(PipelineContext parentContext) {
            return new PipelineContextImpl(parentContext);
        }

        /**
         * 框架不处理任何异常， 请各valve实现者自己处理异常，否则会影响后续valve
         * @throws PipelineException
         */
        @Override
        public void invokeNext() throws PipelineException {
            if (broken) {
                return;
            }
            try {
                executingIndex++;
                executedIndex++;
                if (executingIndex < valves.length) {
                    Valve valve = valves[executingIndex];
                    Stopwatch stopwatch = Stopwatch.createStarted();
                    try {
                        valve.invoke(this);
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

        @Override
        public void invoke() throws PipelineException {
            Preconditions.checkState(!isBroken(),"cannot reinvoke a broken pipeline");
            executingIndex = executedIndex = -1;
            invokeNext();
        }

        @Override
        public boolean isBroken() {
            return broken;
        }

        @Override
        public boolean isFinished() {
            return !broken && executedIndex >= valves.length;
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

}