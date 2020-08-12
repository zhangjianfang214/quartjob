package com.quartjob.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quartjob.core.dao.quartz.QuartzJobDao;
import com.quartjob.core.dto.quart.QuartzJob;
import com.quartjob.core.entity.quartz.JobEntity;
import com.quartjob.core.service.QuartJobService;
import com.quartjob.core.utils.BizExceptionEnum;
import com.quartjob.core.utils.ConstantMsgs;
import com.quartjob.core.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Slf4j
public class QuartJobServiceImpl extends ServiceImpl<QuartzJobDao,JobEntity> implements QuartJobService {

    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

    @Autowired
    private QuartzJobDao quartzJobDao;

    @Override
    public void addJob(JobEntity jobEntity) {
        jobEntity.setStatus(ConstantMsgs.Status.NOMAL);
        quartzJobDao.insertJobEntity(jobEntity);

        addToScheduler(jobEntity);
    }

    @Override
    public void reloadJobs() {
        QueryWrapper<JobEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(JobEntity::getStatus,ConstantMsgs.Status.NOMAL);
        List<JobEntity> list = quartzJobDao.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            list.forEach(x->addToScheduler(x));
        }
    }

    @Override
    public ResponseEntity findJobs() {
        QueryWrapper<JobEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(JobEntity::getStatus,ConstantMsgs.Status.NOMAL);
        List<JobEntity> list = quartzJobDao.selectList(queryWrapper);
        return new ResponseEntity(ConstantMsgs.SUCCESS,list.size(),list);
    }

    @Override
    public void modifyJobTime(JobEntity jobEntity) {
        quartzJobDao.updateById(jobEntity);

        String jobName = jobEntity.getName();
        String time = jobEntity.getTime();
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            TriggerKey triggerKey = new TriggerKey(jobName,TRIGGER_GROUP_NAME);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                removeJob(jobEntity);
                addJob(jobEntity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity removeJob(JobEntity jobEntity) {
        JobEntity job = quartzJobDao.selectById(jobEntity.getId());
        if(job == null){
            return new ResponseEntity(ConstantMsgs.FAIL, BizExceptionEnum.UNKNOWN_ENTITY.getCode(), BizExceptionEnum.UNKNOWN_ENTITY.getMsg());
        }
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            TriggerKey triggerKey = new TriggerKey(job.getName(),TRIGGER_GROUP_NAME);
            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            JobKey jobKey = new JobKey(job.getName(),TRIGGER_GROUP_NAME);
            sched.deleteJob(jobKey);// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity(ConstantMsgs.SUCCESS);
    }

    @Override
    public void startJob(JobEntity jobEntity) {
        addToScheduler(jobEntity);
    }

    /**
     * 添加到定时任务触发器
     * @param jobEntity
     */
    private void addToScheduler(@RequestBody JobEntity jobEntity) {
        String jobName = jobEntity.getName();
        String time = jobEntity.getTime();
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("url",jobEntity.getUrl());
            jobDataMap.put("httpMethod",jobEntity.getHttpMethod());
            jobDataMap.put("params",jobEntity.getParams());
            JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class).usingJobData(jobDataMap).withIdentity(jobName, JOB_GROUP_NAME).build();// 任务名，任务组，任务执行类
// 触发器
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME)
                    .withSchedule(CronScheduleBuilder.cronSchedule(time)).build();// 触发器名,触发器组
            sched.scheduleJob(jobDetail, trigger);
// 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
