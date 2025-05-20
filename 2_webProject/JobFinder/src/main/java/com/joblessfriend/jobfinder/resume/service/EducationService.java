package com.joblessfriend.jobfinder.resume.service;

import java.util.List;

import com.joblessfriend.jobfinder.resume.domain.EducationVo;


public interface EducationService {
	
	public List<EducationVo> educationSelectList(int resumeId);
	public void educationInsertOne(EducationVo educationVo);
	public void educationDeleteOne(int eduId, int resumeId);
}
