package com.aikon.wht.exception;

import lombok.Data;

/**
 * @author haitao.wang
 */
@Data
public class InactiveException extends Exception {

    private Object param;

    public InactiveException(String message) {
        super(message);
    }

    public InactiveException(Object param) {
        this.param = param;
    }
}
