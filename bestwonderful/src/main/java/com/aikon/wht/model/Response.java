package com.aikon.wht.model;

import lombok.Data;

/**
 * @author haitao.wang
 */
@Data
public class Response<T>{

    Integer status = 1;

    String message;

    T data;

    public Response() {
    }

    public Response(T data) {
        this.data = data;
    }

    public Response(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public final static Response SUCCESS = new Response(1,"");

}
