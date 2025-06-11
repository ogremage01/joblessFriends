package com.joblessfriend.jobfinder.resume.dao;

import java.util.List;

import com.joblessfriend.jobfinder.resume.domain.CareerVo;
import com.joblessfriend.jobfinder.resume.domain.CertificateResumeVo;
import com.joblessfriend.jobfinder.resume.domain.EducationVo;
import com.joblessfriend.jobfinder.resume.domain.PortfolioVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.domain.SchoolVo;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;

public interface ResumeDao {
	List<ResumeVo> findResumesByMemberId(int memberId);
	
    void deleteResumeById(int memberId, int resumeId);
    
	void updateProfileImage(int resumeId, int memberId, String imageUrl);

	ResumeVo getResumeByResumeId(int resumeId);
	ResumeVo getResumeByResumeCopyId(int resumeId);
    // 이력서 전체 정보 조회 (모든 하위 데이터 포함)
    ResumeVo getResumeWithAllDetails(int resumeId);
    ResumeVo getResumeCopyWithAllDetails(int resumeId);
    // 하위 데이터 개별 조회
    List<SchoolVo> getSchoolsByResumeId(int resumeId);
    List<CareerVo> getCareersByResumeId(int resumeId);
    List<EducationVo> getEducationsByResumeId(int resumeId);
    List<PortfolioVo> getPortfoliosByResumeId(int resumeId);
    List<CertificateResumeVo> getCertificateByResumeId(int resumeId);
    List<SkillVo> getTagIdsByResumeId(int resumeId);
	
	// 이력서 메인 정보 저장/수정
    void insertResume(ResumeVo resume);
    void updateResume(ResumeVo resume);

    // 학력 정보 저장/수정
    void insertSchool(SchoolVo school);
    void updateSchool(SchoolVo school);
    void deleteSchoolsByResumeId(int resumeId);

    // 교육 이수 정보 저장/수정
    void insertEducation(EducationVo education);
    void updateEducation(EducationVo education);
    void deleteEducationsByResumeId(int resumeId);

    // 경력 정보 저장/수정
    void insertCareer(CareerVo career);
    void updateCareer(CareerVo career);
    void deleteCareersByResumeId(int resumeId);

    // 자격증 연결 정보 저장/수정
    void insertCertificateResume(CertificateResumeVo certificateResumeVo);
    void deleteCertificatesByResumeId(int resumeId);

    // 태그 연결 정보 저장/수정
    void insertResumeTag(int resumeId, Long tagId);
    void deleteTagsByResumeId(int resumeId);

    // 포트폴리오 저장/수정
    void insertPortfolio(PortfolioVo portfolio);
    void updatePortfolio(PortfolioVo portfolio);
    void deletePortfoliosByResumeId(int resumeId);


    int selectCareerGradeScore(int careerJobYear);

}


