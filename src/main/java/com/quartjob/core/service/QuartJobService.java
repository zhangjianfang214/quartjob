package com.quartjob.core.service;

import com.quartjob.core.entity.quartz.JobEntity;
import com.quartjob.core.utils.ResponseEntity;

public interface QuartJobService {

    void addJob(JobEntity jobEntity);

    void reloadJobs();

    ResponseEntity findJobs();

    void modifyJobTime(JobEntity jobEntity);

    ResponseEntity removeJob(JobEntity jobEntity);
}
