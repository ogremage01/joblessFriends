package com.joblessfriend.jobfinder.job.service;


import com.joblessfriend.jobfinder.job.dao.JobDao;
import com.joblessfriend.jobfinder.job.domain.JobVo;
import lombok.Setter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService{


    @Autowired
    private JobDao jobDao;

    @Override
    public JobVo getJobByIdForRecruitment(int jobPostId) {
        return jobDao.getJobByIdForRecruitment(jobPostId);
    }

	
    @Override
    public List<JobVo> selectJobsByGroupId(int jobGroupId) { 
	  // TODOAuto-generated method stub
    	return jobDao.selectJobsByGroupId(jobGroupId);
    }
	 
}
