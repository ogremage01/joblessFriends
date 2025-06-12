package com.joblessfriend.jobfinder.resume.dao;

import com.joblessfriend.jobfinder.recruitment.domain.JobPostAnswerVo;
import com.joblessfriend.jobfinder.recruitment.domain.JobPostQuestionVo;
import com.joblessfriend.jobfinder.resume.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResumeApplyDao {

    // 메인 이력서 복사본 저장
    void insertResumeCopy(ResumeVo resumeVo);

    // 하위 정보 복사 저장
    void insertSchool(SchoolVo schoolVo);
    void insertCareer(CareerVo careerVo);
    void insertEducation(EducationVo educationVo);

    // 자격증 복사는 이름, 발급처, 취득일이 아닌 기존 CERTIFICATE_ID만 참조
    void insertCertificateResume(CertificateResumeVo certificateVo);



    void insertPortfolio(PortfolioVo portfolioVo);

    // 태그 복사용 조회 및 삽입
    List<Integer> getTagIdsByResumeId(int resumeId);
    void insertResumeTagCopy(int resumeId, int tagId);

    void insertResumeManage(ResumeManageVo manageVo);

    List<JobPostQuestionVo> findQuestionsByJobPostId(int jobPostId);
    int insertAnswers(@Param("answerList") List<JobPostAnswerVo> answerList);
    int selectNextAnswerId();
    List<Integer> selectNextAnswerIds(int count);


    int countByMemberAndJobPost(int memberId, int jobPostId);

	ResumeVo getResumeWithAllDetails(int resumeId);

	List<SchoolVo> getSchoolsByResumeId(int resumeId);

	List<CareerVo> getCareersByResumeId(int resumeId);

	List<EducationVo> getEducationsByResumeId(int resumeId);

	List<PortfolioVo> getPortfoliosByResumeId(int resumeId);

	List<CertificateResumeVo> getCertificateByResumeId(int resumeId);
}
