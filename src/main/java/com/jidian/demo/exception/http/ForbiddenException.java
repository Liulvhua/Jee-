package com.jidian.demo.exception.http;

public class ForbiddenException extends HttpException {
    public ForbiddenException(int code) {
        this.code = code;
        this.HttpstatusCode = 200;
    }
}
