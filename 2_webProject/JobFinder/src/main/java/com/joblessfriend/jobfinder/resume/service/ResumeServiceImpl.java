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
	public void saveResume(ResumeSaveRequestVo request, String username) {
		// TODO Auto-generated method stub
		MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
	    if (memberVo == null) {
	        throw new IllegalStateException("로그인된 사용자만 이력서를 저장할 수 있습니다.");
	    }
	    
	    int memberId = memberVo.getMemberId();
	    resumeSaveParser.saveAll(request, memberId);
	}
}
