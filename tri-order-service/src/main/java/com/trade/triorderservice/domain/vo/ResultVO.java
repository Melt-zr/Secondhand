package com.trade.triorderservice.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 通用响应结果VO
 */
@Data
@Accessors(chain = true)
public class ResultVO<T> {
    
    /**
     * 响应码
     */
    private Integer code;
    
    /**
     * 响应消息
     */
    private String message;
    
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 成功响应
     */
    public static <T> ResultVO<T> success() {
        return new ResultVO<T>()
                .setCode(200)
                .setMessage("操作成功");
    }
    
    /**
     * 成功响应带数据
     */
    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<T>()
                .setCode(200)
                .setMessage("操作成功")
                .setData(data);
    }
    
    /**
     * 成功响应带消息和数据
     */
    public static <T> ResultVO<T> success(String message, T data) {
        return new ResultVO<T>()
                .setCode(200)
                .setMessage(message)
                .setData(data);
    }
    
    /**
     * 失败响应
     */
    public static <T> ResultVO<T> error(String message) {
        return new ResultVO<T>()
                .setCode(500)
                .setMessage(message);
    }
    
    /**
     * 失败响应带错误码
     */
    public static <T> ResultVO<T> error(Integer code, String message) {
        return new ResultVO<T>()
                .setCode(code)
                .setMessage(message);
    }
}
