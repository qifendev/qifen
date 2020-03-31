package com.qifen;


import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 返回类
 */

@Data
@Service
public class Result<T> implements Serializable {
    private int code;
    private String message;
    private T result;


    public Result success200Message(String message) {
        this.code = 200;
        this.message = message;
        this.result = null;
        return this;
    }

    public Result<T> success200Result(T result) {
        this.code = 200;
        this.message = "操作成功";
        this.result = result;
        return this;
    }

    public Result error400Message(String message) {
        this.code = 400;
        this.message = message;
        this.result = null;
        return this;
    }

    public Result<T> error400Result(T result) {
        this.code = 400;
        this.message = "操作失败";
        this.result = result;
        return this;
    }

    public Result error500Message(String message) {
        this.code = 500;
        this.message = message;
        this.result = null;
        return this;
    }

    public Result<T> error500Result(T result) {
        this.code = 500;
        this.message = "操作失败";
        this.result = result;
        return this;
    }

}


