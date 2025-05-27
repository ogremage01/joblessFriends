package com.joblessfriend.jobfinder.resume.dao;

import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;

public interface ResumeMatchDao {

	ResumeVo selectResume(int resumeId);

	RecruitmentVo selectRecruitment(int jobPostId);

}
