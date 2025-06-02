package com.joblessfriend.jobfinder.resume.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;

@Repository
public class ResumeMatchDaoImpl implements ResumeMatchDao{

	@Autowired
	private SqlSession sqlSession;
	
	String namespace = "com.joblessfreind.jobfinder.";
	
	@Override
	public ResumeVo selectResume(int resumeId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "resume.selectResume", resumeId);
	}

	@Override
	public RecruitmentVo selectRecruitment(int jobPostId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "recruitment.dao.RecruitmentDao.selectRecruitment", jobPostId);
	}

}
