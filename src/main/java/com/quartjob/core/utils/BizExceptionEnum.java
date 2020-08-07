package com.quartjob.core.utils;

import lombok.Getter;

@Getter
public enum BizExceptionEnum {
    PARAMETER_EXCEPTION("10000000","参数错误"),
    UPDATE_DUPLICATED("10000001" ,"表中已经存在该记录！"),
    UNKNOWN_ENTITY("10000002" ,"未找到指定的信息！");

    private String code;
    private String msg;

    BizExceptionEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCodeInteger(){
        return Integer.valueOf(code);
    }
}
