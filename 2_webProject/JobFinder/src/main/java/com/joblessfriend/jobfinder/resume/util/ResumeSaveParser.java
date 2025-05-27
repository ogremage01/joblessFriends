package com.joblessfriend.jobfinder.resume.util;

import com.joblessfriend.jobfinder.resume.dao.ResumeDao;
import com.joblessfriend.jobfinder.resume.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResumeSaveParser {

    private final ResumeDao resumeDao;

    public void saveAll(ResumeSaveRequestVo request, int memberId) {
        // 1. 이력서 메인 저장
        ResumeVo resume = new ResumeVo();
        resume.setMemberId(memberId);
        resume.setMemberName(request.getMemberName());
        resume.setBirthDate(request.getBirthDate());
        resume.setPhoneNumber(request.getPhoneNumber());
        resume.setEmail(request.getEmail());
        resume.setAddress(request.getAddress());
        resume.setSelfIntroduction(request.getSelfIntroduction());
        resume.setProfile(request.getProfile());
        resume.setPostalCodeId(request.getPostalCodeId());
        resume.setJobGroupId(request.getJobGroupId());
        resume.setJobId(request.getJobId());

        resumeDao.insertResume(resume);
        int resumeId = resume.getResumeId(); // selectKey 방식으로 채워져야 함

        // 2. 학력
        if (request.getSchools() != null) {
            for (SchoolVo school : request.getSchools()) {
                school.setResumeId(resumeId);
                resumeDao.insertSchool(school);
            }
        }

        // 3. 교육
        if (request.getEducations() != null) {
            for (EducationVo edu : request.getEducations()) {
                edu.setResumeId(resumeId);
                resumeDao.insertEducation(edu);
            }
        }

        // 4. 경력
        if (request.getCareers() != null) {
            for (CareerVo career : request.getCareers()) {
                career.setResumeId(resumeId);
                resumeDao.insertCareer(career);
            }
        }

        // 5. 자격증
        if (request.getCertificateIds() != null) {
            for (Long certId : request.getCertificateIds()) {
                resumeDao.insertCertificateResume(resumeId, certId);
            }
        }

        // 6. 태그
        if (request.getTagIds() != null) {
            for (Long tagId : request.getTagIds()) {
                resumeDao.insertResumeTag(resumeId, tagId);
            }
        }

        // 7. 포트폴리오
        if (request.getPortfolios() != null) {
            for (PortfolioVo portfolio : request.getPortfolios()) {
                portfolio.setResumeId(resumeId);
                resumeDao.insertPortfolio(portfolio);
            }
        }
    }
}
