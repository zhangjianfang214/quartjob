package com.quartjob.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quartjob.core.entity.quartz.JobEntity;
import com.quartjob.core.utils.ResponseEntity;

public interface QuartJobService extends IService<JobEntity> {

    void addJob(JobEntity jobEntity);

    void reloadJobs();

    ResponseEntity findJobs();

    void modifyJobTime(JobEntity jobEntity);

    ResponseEntity removeJob(JobEntity jobEntity);

    void startJob(JobEntity jobEntity);
}
