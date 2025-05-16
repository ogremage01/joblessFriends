package com.joblessfriend.jobfinder.recruitment.dao;

import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
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

    @Override
    public List<RecruitmentVo> recruitmentList() {
        return sqlSession.selectList("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.RecruitmentList");
    }

    @Override
    public RecruitmentVo getRecruitmentId(int jobPostId) {
        return sqlSession.selectOne("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.getRecruitmentId",jobPostId);
    }

	@Override
	public int jobPostDelete(List<Integer> jobPostIdList) {
		// TODO Auto-generated method stub
		return sqlSession.delete("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.jobPostDelete", jobPostIdList);
	}

	@Override
	public List<RecruitmentVo> adminRecruitmentList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.adminRecruitmentList");
    }

	@Override
	public List<RecruitmentVo> companyRecruitmentSelectList(int companyId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.companyRecruitmentList",companyId);
	}
	
}
