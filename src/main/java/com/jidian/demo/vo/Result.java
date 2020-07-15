package com.jidian.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private static final int SUCCESS = 200;
//    private static final int FAILED = 500;
    private int code;
    private String message;
    private Object data;
//    public Result(int code, String message, Object data) {
//        this.code = code;
//        this.message = message;
//        this.data = data;
//    }
    public static Result ok(Object data) {
        return new Result(SUCCESS, "success", data);
    }

    public void setSuccess(boolean b) {
    }
}
