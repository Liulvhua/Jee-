package com.jidian.demo.exception.http;

public class ServerException extends HttpException {
    public ServerException(int code) {
        this.HttpstatusCode = 200;
        this.code = code;
    }
}
