package com.joblessfriend.jobfinder.resume.domain;

import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResumeMatchVo {

	ResumeVo resumeVo;
	RecruitmentVo recruitmentVo;
	
	
}
