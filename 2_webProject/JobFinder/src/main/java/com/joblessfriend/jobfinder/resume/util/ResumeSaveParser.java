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
    	System.out.println(">>> [saveAll] 호출됨, memberId = " + memberId);
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

        System.out.println(">>> [saveAll] resumeVo 내용 확인 = " + resume);
        resumeDao.insertResume(resume);
        System.out.println(">>> [saveAll] 이력서 insert 호출 완료됨");
        
        int resumeId = resume.getResumeId(); // selectKey 방식으로 채워져야 함
        System.out.println(">>> [saveAll] 생성된 resumeId = " + resumeId);

        // 2. 학력
        if (request.getSchools() != null) {
        	System.out.println("📌 학력 목록 있음. 사이즈: " + request.getSchools().size());
            for (SchoolVo school : request.getSchools()) {
            	System.out.println("📌 SchoolVo 확인: " + school);
                school.setResumeId(resumeId);
                System.out.println("📌 저장할 school: " + school); // toString() 덕분에 잘 보임
                resumeDao.insertSchool(school);
            }
        }else {
            System.out.println("❌ [학력 입력] request.getSchools() 가 null 또는 empty");
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
