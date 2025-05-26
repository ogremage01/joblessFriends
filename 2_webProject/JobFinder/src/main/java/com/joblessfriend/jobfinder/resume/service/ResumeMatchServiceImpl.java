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
	public ResumeVo selectResume(int resumeId) {
		// TODO Auto-generated method stub
		return resumeMatchDao.selectResume(resumeId);
	}

	@Override
	public RecruitmentVo selectRecruitment(int jobPostId) {
		// TODO Auto-generated method stub
		return resumeMatchDao.selectRecruitment(jobPostId);
	}

}
