package com.joblessfriend.jobfinder.resume.service;

import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;

public interface ResumeMatchService {

	public int calculateMatchScore(int resumeId, int jobPostId);
	int selectCareerGradeScore(int careerJobYear);
}
