package com.joblessfriend.jobfinder.recruitment.dao;

import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecruitmentDaoImpl implements RecruitmentDao {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<JobGroupVo> jobGroupList() {
        return sqlSession.selectList("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.jobGroupList");

    }

    @Override
    public List<JobGroupVo> jobList(int jobGroupId) {
        return sqlSession.selectList("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.jobList",jobGroupId);
    }
}
