package com.joblessfriend.jobfinder.resume.dao;

import com.joblessfriend.jobfinder.resume.domain.*;

import java.util.List;

public interface ResumeApplyDao {

    // 메인 이력서 복사본 저장
    void insertResumeApply(ResumeVo resumeVo);

    // 하위 정보 insert
    void insertSchool(SchoolVo schoolVo);
    void insertCareer(CareerVo careerVo);
    void insertEducation(EducationVo educationVo);
    void insertCertificateResume(int resumeId, int certificateId);
    void insertPortfolio(PortfolioVo portfolioVo);
    List<Integer> getTagIdsByResumeId(int resumeId);
    void insertResumeTagCopy(int resumeId, int tagId);

    // (조회 메서드는 기존 그대로 유지)
}

