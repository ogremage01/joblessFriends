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

    @Override
    public JobVo getJobById(int jobPostId) {
        return sqlSession.selectOne("com.joblessfriend.jobfinder.job.dao.JobDao.getJobById", jobPostId);

    }
    @Override
    public JobVo getJobByIdForRecruitment(int jobPostId) {
        return sqlSession.selectOne("com.joblessfriend.jobfinder.job.dao.JobDao.getJobByIdForRecruitment", jobPostId);

    }
    @Override
    public List<JobVo> selectJobsByGroupId(int jobGroupId) {
        // TODOAuto-generated method stub
        return sqlSession.selectList("com.joblessfriend.jobfinder.job.dao.JobDao.selectJobsByGroupId", jobGroupId);
    }
}
