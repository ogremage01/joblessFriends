package com.joblessfriend.jobfinder.job.service;


import com.joblessfriend.jobfinder.job.dao.JobDao;
import com.joblessfriend.jobfinder.job.domain.JobVo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService{


    @Autowired
    private JobDao jobDao;

    @Override
    public JobVo getJobById(int jobPostId) {
        return jobDao.getJobById(jobPostId);
    }
}
