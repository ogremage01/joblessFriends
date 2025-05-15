package com.joblessfriend.jobfinder.resume.service;

import java.util.List;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;

public interface ResumeService {

	List<ResumeVo> getResumesByMemberId(int memberId);
	
    void deleteResume(int memberId, int resumeId);
    
    void updateProfileImage(int resumeId, int memberId, String imageUrl);
}
