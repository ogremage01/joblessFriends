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
	
	String namespace = "com.joblessfreind.jobfinder.resume.";
	
	@Override
	public int selectCareerGradeScore(int careerJobYear) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "selectCareerGradeScore", careerJobYear);

	}

}
