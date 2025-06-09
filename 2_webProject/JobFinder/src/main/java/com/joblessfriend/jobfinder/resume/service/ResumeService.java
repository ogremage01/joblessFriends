package com.joblessfriend.jobfinder.resume.service;

import java.util.List;
import java.util.Map;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;

import jakarta.servlet.http.HttpSession;

public interface ResumeService {

// 기존
    List<ResumeVo> getResumesByMemberId(int memberId, int jobPostId);
    List<ResumeVo> getResumesByMemberId(int memberId);
	ResumeVo getResumeByResumeId(int resumeId);
	
	ResumeVo getResumeWithAllDetails(int resumeId);
    ResumeVo getResumeCopyWithAllDetails(int resumeId);
    void deleteResume(int memberId, int resumeId);
    
    void updateProfileImage(int resumeId, int memberId, String imageUrl);

	void saveResume(ResumeVo resumeVo);
    
    void saveResumeWithDetails(ResumeVo resumeVo);
    
    void updateResume(ResumeVo resumeVo);
    
    List<ResumeVo> getResumeListWithSummaryByMemberId(int memberId);

    // 비즈니스 로직 메서드들
    Map<String, Object> prepareResumeWritePageData(Integer resumeId, MemberVo loginUser);
    String saveOrUpdateResume(Map<String, Object> requestMap, MemberVo loginUser);
    String saveOrUpdateResume(Map<String, Object> requestMap, MemberVo loginUser, HttpSession session);
    boolean checkResumeOwnership(int resumeId, int memberId);
    ResumeVo prepareResumePreviewData(Map<String, Object> requestMap, MemberVo loginUser, HttpSession session);
    List<Map<String, String>> prepareJobTitleData(ResumeVo resume);
    void enrichSkillData(ResumeVo resume);
    
}
