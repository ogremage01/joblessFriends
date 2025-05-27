package com.joblessfriend.jobfinder.resume.dao;

import java.util.List;

import com.joblessfriend.jobfinder.resume.domain.CareerVo;
import com.joblessfriend.jobfinder.resume.domain.EducationVo;
import com.joblessfriend.jobfinder.resume.domain.PortfolioVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.domain.SchoolVo;

public interface ResumeDao {
	List<ResumeVo> findResumesByMemberId(int memberId);
	
    void deleteResumeById(int memberId, int resumeId);
    
	void updateProfileImage(int resumeId, int memberId, String imageUrl);

	ResumeVo getResumeByResumeId(int resumeId);
	
	// 이력서 메인 정보 저장
    void insertResume(ResumeVo resume);

    // 학력 정보 저장
    void insertSchool(SchoolVo school);

    // 교육 이수 정보 저장
    void insertEducation(EducationVo education);

    // 경력 정보 저장
    void insertCareer(CareerVo career);

    // 자격증 연결 정보 저장
    void insertCertificateResume(int resumeId, Long certificateId);

    // 태그 연결 정보 저장
    void insertResumeTag(int resumeId, Long tagId);

    // 포트폴리오 저장
    void insertPortfolio(PortfolioVo portfolio);

}
