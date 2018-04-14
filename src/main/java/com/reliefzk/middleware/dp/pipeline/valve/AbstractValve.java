package com.reliefzk.middleware.dp.pipeline.valve;
import com.reliefzk.middleware.dp.pipeline.PipelineContext;
import com.reliefzk.middleware.dp.pipeline.Valve;

import java.util.HashMap;
import java.util.Map;

/**
 * valve抽象类
 *    1.0 空实现
 * @author kui.zhouk
 */
public abstract class AbstractValve implements Valve {

    /**
     * 执行顺序
     */
    private int order = 0;

    /**
     * 业务值为空则返回默认值
     * @param val 业务值
     * @param defaultVal 默认值域
     * @param <T> 类型
     * @return
     */
    protected <T> T defaultIfNull(T val, T defaultVal){
        if(val != null){
            return val;
        }
        return defaultVal;
    }

    /**
     * 如果是map类型的response，此方法快速支持
     * @param key
     * @param value
     */
    protected void putResponse(PipelineContext pipelineContext, String key, String value){
        Map<String, Object> response = pipelineContext.getRequestBody();
        if(response == null){
            response = new HashMap<>();
        }
        response.put(key, value);
        pipelineContext.setResponseBody(response);
    }

    @Override
    public int order(){
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
