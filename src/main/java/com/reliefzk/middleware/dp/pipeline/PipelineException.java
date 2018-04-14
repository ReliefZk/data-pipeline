package com.reliefzk.middleware.dp.pipeline;

/**
 * 代表在pipeline中发生的异常
 * @author kui.zhouk
 */
public class PipelineException extends RuntimeException {

    private static final long serialVersionUID = 6405155399147146236L;

    public PipelineException() {
        super();
    }

    public PipelineException(String message, Throwable cause) {
        super(message, cause);
    }

    public PipelineException(String message) {
        super(message);
    }

    public PipelineException(Throwable cause) {
        super(cause);
    }

}