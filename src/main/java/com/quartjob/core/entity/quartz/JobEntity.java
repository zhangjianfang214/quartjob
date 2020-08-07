package com.quartjob.core.entity.quartz;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("AMOS_QUARTZ_JOB")
public class JobEntity {
    private Integer id;
    private String name;
    private String httpMethod;
    private String url;
    /**
     * 时间设置，参考quartz说明文档
     */
    private String time;
    /**
     * post请求时为json格式
     */
    private String params;
    private Date createTime;
    private Date updateTime;
    private Long createUser;
    private Long updateUser;
    private Integer status;
}
