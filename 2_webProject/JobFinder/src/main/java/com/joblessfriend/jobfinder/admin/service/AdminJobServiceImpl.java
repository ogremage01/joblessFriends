package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.admin.dao.AdminJobDao;
import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Service
public class AdminJobServiceImpl implements AdminJobService {
    
    @Autowired
    private AdminJobDao adminJobDao;
    
    @Override
    public List<JobVo> getJobList(SearchVo searchVo) {
        return adminJobDao.getJobList(searchVo);
    }
    
    @Override
    public int getJobCount(SearchVo searchVo) {
        return adminJobDao.getJobCount(searchVo);
    }
    
    @Override
    public int insertJob(String jobName, int jobGroupId) {
        return adminJobDao.insertJob(jobName, jobGroupId);
    }
    
    @Override
    public int deleteJob(int jobId) {
        return adminJobDao.deleteJob(jobId);
    }
    
    @Override
    public int deleteJobs(List<Integer> jobIdList) {
        return adminJobDao.deleteJobs(jobIdList);
    }
    
    @Override
    public List<JobGroupVo> getAllJobGroups() {
        return adminJobDao.getAllJobGroups();
    }
} 