package com.joblessfriend.jobfinder.resume.service;

import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;

public interface ResumeMatchService {

	int selectCareerGradeScore(int careerJobYear);
	
}
