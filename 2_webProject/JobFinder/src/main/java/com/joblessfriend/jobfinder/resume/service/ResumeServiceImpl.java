package com.joblessfriend.jobfinder.resume.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.resume.dao.ResumeDao;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;

@Service
public class ResumeServiceImpl implements ResumeService{
	
	@Autowired
	private ResumeDao resumeDao;

	@Override
	public List<ResumeVo> getResumesByMemberId(int memberId) {
	    return resumeDao.findResumesByMemberId(memberId);
	}

	@Override
	public void deleteResume(int memberId, int resumeId) {
	    resumeDao.deleteResumeById(memberId, resumeId);
	}

	@Override
	public void updateProfileImage(int resumeId, int memberId, String imageUrl) {
		// TODO Auto-generated method stub
		resumeDao.updateProfileImage(resumeId, memberId, imageUrl);
	}

	@Override
	public ResumeVo getResumeByResumeId(int resumeId) {
		// TODO Auto-generated method stub
		return resumeDao.getResumeByResumeId(resumeId);
	}
}
