package com.quartjob.core.utils;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjianfang
 * @classname ConstantMsgs
 * @description
 * @date 2019/11/18 11:04
 **/
public class ConstantMsgs {

    public static final String SUCCESS="1";
    public static final String FAIL="0";

    /**
     * 状态
     */
    public static class Status {
        // 草稿
        public static final Integer DRAFT = 0;
        // 正常
        public static final Integer NOMAL = 1;
        // 删除
        public static final Integer DELETE = 2;
    }

}
