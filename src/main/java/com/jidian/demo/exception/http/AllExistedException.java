package com.jidian.demo.exception.http;

public class AllExistedException extends HttpException {
    public AllExistedException(int code){
        this.code = code;
        this.HttpstatusCode = 200;
    }
}
