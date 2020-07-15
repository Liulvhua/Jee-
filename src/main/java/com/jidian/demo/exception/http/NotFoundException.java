package com.jidian.demo.exception.http;

public class NotFoundException extends HttpException {
    public NotFoundException(int code) {
        this.code = code;
        this.HttpstatusCode = 200;
    }
}
