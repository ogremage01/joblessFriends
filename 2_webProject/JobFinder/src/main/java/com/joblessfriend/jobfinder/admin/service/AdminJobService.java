package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.util.SearchVo;

public interface AdminJobService {
    
    List<JobVo> getJobList(SearchVo searchVo);
    
    int getJobCount(SearchVo searchVo);
    
    int insertJob(String jobName, int jobGroupId);
    
    int deleteJob(int jobId);
    
    int deleteJobs(List<Integer> jobIdList);
    
    List<JobGroupVo> getAllJobGroups();
} 