package com.joblessfriend.jobfinder.resume.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.resume.dao.ResumeMatchDao;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;

@Service
public class ResumeMatchServiceImpl implements ResumeMatchService{
	
	@Autowired
	ResumeMatchDao resumeMatchDao;

	@Override
	public int selectCareerGradeScore(int careerJobYear) {
		// TODO Auto-generated method stub
		return resumeMatchDao.selectCareerGradeScore(careerJobYear);
	}
	

}
