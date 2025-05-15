package com.joblessfriend.jobfinder.resume.dao;

import java.util.List;

import com.joblessfriend.jobfinder.resume.domain.ResumeVo;

public interface ResumeDao {
	List<ResumeVo> findResumesByMemberId(int memberId);
	
    void deleteResumeById(int memberId, int resumeId);
    
	void updateProfileImage(int resumeId, int memberId, String imageUrl);

}
