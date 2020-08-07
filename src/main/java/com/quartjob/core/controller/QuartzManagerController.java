package com.quartjob.core.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quartjob.core.dao.quartz.AmosQuartzDao;
import com.quartjob.core.dto.quart.QuartzJob;
import com.quartjob.core.entity.quartz.JobEntity;
import com.quartjob.core.service.QuartJobService;
import com.quartjob.core.utils.BizExceptionEnum;
import com.quartjob.core.utils.ConstantMsgs;
import com.quartjob.core.utils.ResponseEntity;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quartzManager")
public class QuartzManagerController {
    @Autowired
    private QuartJobService quartJobService;


    /**
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     *
     * @param jobEntity
     * 时间设置，参考quartz说明文档
     *
     * @Title: QuartzManagerController.java
     * @Copyright: Copyright (c) 2014
     *
     * @author Comsys-LZP
     * @date 2014-6-26 下午03:47:44
     * @version V2.0
     */
    @PostMapping("addJob")
    public void addJob(@RequestBody JobEntity jobEntity) {

        quartJobService.addJob(jobEntity);
    }

    @GetMapping("reloadJobs")
    public void reloadJobs(){

        quartJobService.reloadJobs();

    }


    @GetMapping("findJobs")
    public ResponseEntity findJobs(){
        return quartJobService.findJobs();
    }

    /**
     * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param jobEntity
     *
     * @Title: QuartzManagerController.java
     * @Copyright: Copyright (c) 2014
     *
     * @author Comsys-LZP
     * @date 2014-6-26 下午03:49:21
     * @version V2.0
     */
    @PostMapping("modifyJobTime")
    public void modifyJobTime(@RequestBody JobEntity jobEntity) {

        quartJobService.modifyJobTime(jobEntity);
    }
    /**
     * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param jobEntity id
     *
     * @Title: QuartzManagerController.java
     * @Copyright: Copyright (c) 2014
     *
     * @author Comsys-LZP
     * @date 2014-6-26 下午03:49:51
     * @version V2.0
     */
    @PostMapping("removeJob")
    public ResponseEntity removeJob(@RequestBody JobEntity jobEntity) {
        return quartJobService.removeJob(jobEntity);
    }

    /**
     * @Description:启动所有定时任务
     *
     *
     * @Title: QuartzManagerController.java
     * @Copyright: Copyright (c) 2014
     *
     * @author Comsys-LZP
     * @date 2014-6-26 下午03:50:18
     * @version V2.0
     */
    @PostMapping("startJobs")
    public ResponseEntity startJobs(@RequestBody JobEntity jobEntity) {
        JobEntity job = amosQuartzDao.selectById(jobEntity.getId());
        if(job == null){
            return new ResponseEntity(ConstantMsgs.FAIL, BizExceptionEnum.UNKNOWN_ENTITY.getCode(), BizExceptionEnum.UNKNOWN_ENTITY.getMsg());
        }
        addToScheduler(job);
        return new ResponseEntity(ConstantMsgs.SUCCESS);
    }
    /**
     * @Description:关闭所有定时任务
     *
     *
     * @Title: QuartzManagerController.java
     * @Copyright: Copyright (c) 2014
     *
     * @author Comsys-LZP
     * @date 2014-6-26 下午03:50:26
     * @version V2.0
     */
    @PostMapping("shutdownJobs")
    public void shutdownJobs() {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("test")
    public void test(){
        System.out.println("定时任务测试...");
    }
}