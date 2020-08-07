package com.quartjob.core.dto.quart;

import com.alibaba.fastjson.JSONObject;
import com.quartjob.core.utils.HttpClientUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @Description: 任务执行类
 *
 * @ClassName: QuartzJob
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-LZP
 * @date 2014-6-26 下午03:37:11
 * @version V2.0
 */
public class QuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext arg0) {
        JobDataMap jobDataMap = arg0.getJobDetail().getJobDataMap();
        if(jobDataMap == null || CollectionUtils.isEmpty(jobDataMap)){
            return;
        }
        String url = jobDataMap.getString("url");
        String httpMethod = jobDataMap.getString("httpMethod");
        if(httpMethod.toUpperCase().equals("GET")){
            HttpClientUtil.doGet(url, "UTF-8");
        }else{
            String params = jobDataMap.getString("params");
            JSONObject jb = new JSONObject();
            if(!StringUtils.isEmpty(params)){
                jb.put("commentId",params);
            }else{
                jb.put("commentId","");
            }
            HttpClientUtil.doPost(url,jb);
        }
    }
}
