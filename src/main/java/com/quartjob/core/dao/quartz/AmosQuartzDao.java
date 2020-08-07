package com.quartjob.core.dao.quartz;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quartjob.core.entity.quartz.JobEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @version : 1.0
 * @author Kangning Wang
 * @date : 2019/11/21
 **/

@Mapper
@Repository
public interface AmosQuartzDao extends BaseMapper<JobEntity> {

    void insertJobEntity(JobEntity jobEntity);

}
