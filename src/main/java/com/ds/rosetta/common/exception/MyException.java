package com.ds.rosetta.common.exception;

import lombok.Data;

/**
 * 自定义异常类
 */
@Data
public class MyException extends RuntimeException {
    /** 错误码 */
    private Integer code;
    
    /**
     * 使用默认错误码构造异常
     * @param message 错误信息
     */
    public MyException(String message) {
        super(message);
        this.code = 500;
    }
    
    /**
     * 使用自定义错误码构造异常
     * @param code 错误码
     * @param message 错误信息
     */
    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
    }
} 