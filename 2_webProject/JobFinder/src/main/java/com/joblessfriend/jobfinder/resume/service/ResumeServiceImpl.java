package com.joblessfriend.jobfinder.resume.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joblessfriend.jobfinder.member.dao.MemberDao;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.resume.dao.ResumeDao;
import com.joblessfriend.jobfinder.resume.domain.ResumeSaveRequestVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.util.ResumeSaveParser;

import jakarta.servlet.http.HttpSession;

@Service
public class ResumeServiceImpl implements ResumeService{
	
	@Autowired
	private ResumeDao resumeDao;
	
	@Autowired
    private ResumeSaveParser resumeSaveParser;

    @Autowired
    private MemberDao memberDao;
    
    @Autowired
    private HttpSession session;

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

	@Override
	@Transactional
	public void saveResume(ResumeSaveRequestVo request, int memberId) {
		// TODO Auto-generated method stub
		System.out.println(">>> [ResumeServiceImpl] saveResume 호출됨");
	    System.out.println(">>> 저장할 memberId = " + memberId);

	    resumeSaveParser.saveAll(request, memberId);
	}
}
