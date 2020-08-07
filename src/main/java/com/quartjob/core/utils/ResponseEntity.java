package com.quartjob.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ResponseEntity implements Serializable {
    public ResponseEntity(){}

    public ResponseEntity(String status){
        this.status = status;
    }

    public ResponseEntity(String status, String code, String msg){
        this.status = status;
        this.code = code;
        this.message = msg;
    }

    public ResponseEntity(String status, Object data){
        this.status=status;
        this.data=data;
    }

    public ResponseEntity(String status, Integer total, Object data){
        this.status = status;
        this.total = total;
        this.data = data;
    }
    /**
     * 1-成功，0-失败
     */
    private String status;
    /**
     * 返回信息码，主要标识错误码
     */
    private String code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 消息主题
     */
    private Object data;

    private Integer total;
}
