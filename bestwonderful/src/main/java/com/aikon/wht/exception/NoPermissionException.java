package com.aikon.wht.exception;

/**
 * @author haitao.wang
 */
public class NoPermissionException extends Exception {
    public NoPermissionException() {
        super("权限不足");
    }
}
