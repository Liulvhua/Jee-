package com.jidian.demo.exception.http;

public class HttpException extends RuntimeException {
    protected Integer code;
    protected Integer HttpstatusCode;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getHttpstatusCode() {
        return HttpstatusCode;
    }

    public void setHttpstatusCode(Integer httpstatusCode) {
        HttpstatusCode = httpstatusCode;
    }
}
