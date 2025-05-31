package com.joblessfriend.jobfinder.resume.dao;

import com.joblessfriend.jobfinder.resume.domain.*;

import java.util.List;

public interface ResumeApplyDao {

    // 메인 이력서 복사본 저장
    void insertResumeCopy(ResumeVo resumeVo);

    // 하위 정보 복사 저장
    void insertSchool(SchoolVo schoolVo);
    void insertCareer(CareerVo careerVo);
    void insertEducation(EducationVo educationVo);

    // 자격증 복사는 이름, 발급처, 취득일이 아닌 기존 CERTIFICATE_ID만 참조
    void insertCertificateResume(int resumeId, int certificateId);

    void insertPortfolio(PortfolioVo portfolioVo);

    // 태그 복사용 조회 및 삽입
    List<Integer> getTagIdsByResumeId(int resumeId);
    void insertResumeTagCopy(int resumeId, int tagId);

    void insertResumeManage(ResumeManageVo manageVo);
}
