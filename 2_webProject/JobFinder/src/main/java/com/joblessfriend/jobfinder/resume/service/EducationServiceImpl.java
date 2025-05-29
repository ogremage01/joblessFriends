package com.joblessfriend.jobfinder.resume.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.resume.dao.EducationDao;
import com.joblessfriend.jobfinder.resume.domain.EducationVo;

@Service
public class EducationServiceImpl implements EducationService{

	public EducationDao educationDao;
	
	@Override
	public List<EducationVo> educationSelectList(int resumeId) {
		// TODO Auto-generated method stub
		return educationDao.educationSelectList(resumeId);
	}

	@Override
	public void educationInsertOne(EducationVo educationVo) {
		// TODO Auto-generated method stub
		educationDao.educationInsertOne(educationVo);
	}

	@Override
	public void educationDeleteOne(int eduId, int resumeId) {
		// TODO Auto-generated method stub
		educationDao.educationDeleteOne(eduId, resumeId);
	}

	@Override
	public void educationUpdateOne(EducationVo educationVo) {
		// TODO Auto-generated method stub
		educationDao.educationUpdateOne(educationVo);
	}

}
