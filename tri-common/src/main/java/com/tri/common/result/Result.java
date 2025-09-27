package com.tri.common.result;


import lombok.Data;

@Data
public class Result<T> {

    private Integer code;
    private String message;
    private T data;


    public static <T> Result<T> success(){
        Result result = new Result<T>();
        result.setCode(1);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(1);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage(message);
        return result;
    }

}
