package com.aikon.wht.exception;

/**
 * 权限不足异常.
 *
 * @author haitao.wang
 */
public class NoPermissionException extends Exception {
    public NoPermissionException() {
        super("权限不足");
    }
}
