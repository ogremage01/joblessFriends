package com.joblessfriend.jobfinder.resume.dao;

import java.util.List;

import com.joblessfriend.jobfinder.resume.domain.EducationVo;

public interface EducationDao {

	List<EducationVo> educationSelectList(int resumeId);

	void educationInsertOne(EducationVo educationVo);

	void educationDeleteOne(int eduId, int resumeId);

}
