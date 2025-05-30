package com.joblessfriend.jobfinder.job.dao;

import com.joblessfriend.jobfinder.job.domain.JobVo;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JobDaoImpl implements JobDao {


    @Autowired
    private SqlSession sqlSession;
    
    private static final String namespace = "com.joblessfriend.jobfinder.job.dao.JobDao.";


    @Override
    public JobVo getJobById(int jobPostId) {
        return sqlSession.selectOne(namespace + "getJobById", jobPostId);

    }
    @Override
    public JobVo getJobByIdForRecruitment(int jobPostId) {
        return sqlSession.selectOne(namespace + "getJobByIdForRecruitment", jobPostId);

    }
    @Override
    public List<JobVo> selectJobsByGroupId(int jobGroupId) {
        // TODOAuto-generated method stub
        return sqlSession.selectList(namespace + "selectJobsByGroupId", jobGroupId);
    }
	@Override
	public String getJobNameById(int jobId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "getJobNameById", jobId);
	}
}
