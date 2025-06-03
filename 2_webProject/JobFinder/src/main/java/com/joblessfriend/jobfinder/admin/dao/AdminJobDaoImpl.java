package com.joblessfriend.jobfinder.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Repository
public class AdminJobDaoImpl implements AdminJobDao {
    
    private Logger logger = LoggerFactory.getLogger(AdminJobDaoImpl.class);
    
    @Autowired
    private SqlSession sqlSession;
    
    private String namespace = "com.joblessfriend.jobfinder.admin.dao.AdminJobDao.";
    
    @Override
    public List<JobVo> getJobList(SearchVo searchVo) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", searchVo.getKeyword());
        params.put("startRow", searchVo.getStartRow());
        params.put("endRow", searchVo.getEndRow());
        
        return sqlSession.selectList(namespace + "getJobList", params);
    }
    
    @Override
    public int getJobCount(SearchVo searchVo) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", searchVo.getKeyword());
        
        return sqlSession.selectOne(namespace + "getJobCount", params);
    }
    
    @Override
    public int insertJob(String jobName, int jobGroupId) {
        Map<String, Object> params = new HashMap<>();
        params.put("jobName", jobName);
        params.put("jobGroupId", jobGroupId);
        
        return sqlSession.insert(namespace + "insertJob", params);
    }
    
    @Override
    public int deleteJob(int jobId) {
        return sqlSession.delete(namespace + "deleteJob", jobId);
    }
    
    @Override
    public int deleteJobs(List<Integer> jobIdList) {
        return sqlSession.delete(namespace + "deleteJobs", jobIdList);
    }
    
    @Override
    public List<JobGroupVo> getAllJobGroups() {
        return sqlSession.selectList(namespace + "getAllJobGroups");
    }
} 