package com.jidian.demo.exception.http;

public class PasswordException extends HttpException{
    public PasswordException(int code){
        this.code = code;
        this.HttpstatusCode = 200;
    }
}
