package com.joblessfriend.jobfinder.resume.service;

import com.joblessfriend.jobfinder.recruitment.domain.JobPostQuestionVo;
import com.joblessfriend.jobfinder.resume.dao.ResumeApplyDao;
import com.joblessfriend.jobfinder.resume.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResumeApplyServiceImpl implements ResumeApplyService {

    @Autowired
    private ResumeApplyDao resumeApplyDao;

    @Autowired
    private ResumeService resumeService;

    @Override
    @Transactional
    public int applyResumeWithCopy(int resumeId,int jobPostId,int memberId) {
        // 1. 원본 이력서 전체 조회
        ResumeVo origin = resumeService.getResumeWithAllDetails(resumeId);

        // 2. 복사용 ResumeVo 생성 및 값 복사
        ResumeVo applyCopy = new ResumeVo();
        applyCopy.setMemberId(memberId);
        applyCopy.setMemberName(origin.getMemberName());
        applyCopy.setBirthDate(origin.getBirthDate());
        applyCopy.setPhoneNumber(origin.getPhoneNumber());
        applyCopy.setEmail(origin.getEmail());
        applyCopy.setSelfIntroduction(origin.getSelfIntroduction());
        applyCopy.setProfile(origin.getProfile());


        applyCopy.setSchoolList(origin.getSchoolList());
        applyCopy.setCareerList(origin.getCareerList());
        applyCopy.setEducationList(origin.getEducationList());
        applyCopy.setCertificateList(origin.getCertificateList());
        applyCopy.setPortfolioList(origin.getPortfolioList());

        // 3. 메인 resume apply insert
        resumeApplyDao.insertResumeCopy(applyCopy);
        int applyId = applyCopy.getResumeId();

        // 4. 하위 테이블 insert
        List<SchoolVo> schools = applyCopy.getSchoolList();
        if (schools != null) {
            for (SchoolVo s : schools) {
                s.setResumeId(applyId);
                resumeApplyDao.insertSchool(s);
            }
        }

        List<CareerVo> careers = applyCopy.getCareerList();
        if (careers != null) {
            for (CareerVo c : careers) {
                c.setResumeId(applyId);
                resumeApplyDao.insertCareer(c);
            }
        }

        List<EducationVo> educations = applyCopy.getEducationList();
        if (educations != null) {
            for (EducationVo e : educations) {
                e.setResumeId(applyId);
                resumeApplyDao.insertEducation(e);
            }
        }

//       List<CertificateResumeVo> certificates = applyCopy.getCertificateList();
//        if (certificates != null) {
//           for (CertificateResumeVo cert : certificates) {
//               resumeApplyDao.insertCertificateResume(applyId, cert.getCertificateId());
//           }
//        }

        List<PortfolioVo> portfolios = applyCopy.getPortfolioList();
        if (portfolios != null) {
            for (PortfolioVo p : portfolios) {
                p.setResumeId(applyId);
                resumeApplyDao.insertPortfolio(p);
            }
        }
        //  스킬 태그 복사 추가
        List<Integer> tagIds = resumeApplyDao.getTagIdsByResumeId(resumeId);
        if (tagIds != null) {
            for (Integer tagId : tagIds) {
                resumeApplyDao.insertResumeTagCopy(applyId, tagId);
            }
        }

        // ✅ 6. 지원 관리 테이블에 insert (복사된 이력서 기준으로)
        ResumeManageVo manageVo = new ResumeManageVo();
        manageVo.setJobPostId(jobPostId);
        manageVo.setMemberId(memberId);
        manageVo.setResumeFile(String.valueOf(applyId)); // 또는 applyId 자체로 처리
        manageVo.setStateId(1); // 지원 완료 상태

        resumeApplyDao.insertResumeManage(manageVo);
        return applyId;
    }


    public List<JobPostQuestionVo> getQuestionsByJobPostId(int jobPostId) {
        return resumeApplyDao.findQuestionsByJobPostId(jobPostId);
    }

}
