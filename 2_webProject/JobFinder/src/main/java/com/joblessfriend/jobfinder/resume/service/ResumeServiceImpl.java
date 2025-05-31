package com.joblessfriend.jobfinder.resume.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joblessfriend.jobfinder.member.dao.MemberDao;
import com.joblessfriend.jobfinder.resume.dao.ResumeDao;
import com.joblessfriend.jobfinder.resume.domain.CareerVo;
import com.joblessfriend.jobfinder.resume.domain.CertificateResumeVo;
import com.joblessfriend.jobfinder.resume.domain.CertificateVo;
import com.joblessfriend.jobfinder.resume.domain.EducationVo;
import com.joblessfriend.jobfinder.resume.domain.PortfolioVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.domain.SchoolVo;

@Service
public class ResumeServiceImpl implements ResumeService {
    
    @Autowired
    private ResumeDao resumeDao;
    
    @Autowired
    private MemberDao memberDao;

    @Override
    public List<ResumeVo> getResumesByMemberId(int memberId) {
        return resumeDao.findResumesByMemberId(memberId);
    }

    @Override
    public void deleteResume(int memberId, int resumeId) {
        resumeDao.deleteResumeById(memberId, resumeId);
    }

    @Override
    public void updateProfileImage(int resumeId, int memberId, String imageUrl) {
        resumeDao.updateProfileImage(resumeId, memberId, imageUrl);
    }

    @Override
    public ResumeVo getResumeByResumeId(int resumeId) {
        return resumeDao.getResumeByResumeId(resumeId);
    }

    @Override
    public ResumeVo getResumeWithAllDetails(int resumeId) {
        System.out.println(">>> [ResumeService] getResumeWithAllDetails 호출, resumeId: " + resumeId);
        
        try {
            ResumeVo resume = resumeDao.getResumeWithAllDetails(resumeId);
            
            if (resume != null) {
                System.out.println(">>> [ResumeService] 이력서 조회 성공, memberName: " + resume.getMemberName());
                System.out.println(">>> [ResumeService] 학력 수: " + (resume.getSchoolList() != null ? resume.getSchoolList().size() : 0));
                System.out.println(">>> [ResumeService] 경력 수: " + (resume.getCareerList() != null ? resume.getCareerList().size() : 0));
                System.out.println(">>> [ResumeService] 교육 수: " + (resume.getEducationList() != null ? resume.getEducationList().size() : 0));
                System.out.println(">>> [ResumeService] 자격증 수: " + (resume.getCertificateList() != null ? resume.getCertificateList().size() : 0));
                System.out.println(">>> [ResumeService] 포트폴리오 수: " + (resume.getPortfolioList() != null ? resume.getPortfolioList().size() : 0));
            } else {
                System.out.println(">>> [ResumeService] 이력서를 찾을 수 없음");
            }
            
            return resume;
        } catch (Exception e) {
            System.err.println(">>> [ResumeService] 이력서 조회 중 오류: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public void saveResume(ResumeVo resumeVo) {
        try {
            // resumeId가 있으면 수정, 없으면 신규 작성
            if (resumeVo.getResumeId() > 0) {
                // 수정 모드
                System.out.println(">>> [ResumeService] 이력서 업데이트 모드, resumeId: " + resumeVo.getResumeId());
                
                // 1. 메인 이력서 정보 업데이트
                resumeDao.updateResume(resumeVo);
                System.out.println(">>> [ResumeService] 메인 이력서 업데이트 완료");
                
                // 2. 기존 하위 데이터 모두 삭제
                deleteResumeDetails(resumeVo.getResumeId());
                
                // 3. 새로운 하위 데이터 저장
                saveResumeDetails(resumeVo, resumeVo.getResumeId());
                
                System.out.println(">>> [ResumeService] 전체 이력서 업데이트 완료");
            } else {
                // 신규 작성 모드
                System.out.println(">>> [ResumeService] 신규 이력서 작성 모드");
                
                // 1. 메인 이력서 정보 저장
                resumeDao.insertResume(resumeVo);
                int resumeId = resumeVo.getResumeId();
                
                System.out.println(">>> [ResumeService] 메인 이력서 저장 완료, resumeId: " + resumeId);
                
                // 2. 하위 데이터 저장
                saveResumeDetails(resumeVo, resumeId);
                
                System.out.println(">>> [ResumeService] 전체 이력서 저장 완료");
            }
            
        } catch (Exception e) {
            System.err.println(">>> [ResumeService] 이력서 저장/수정 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("이력서 저장/수정 중 오류가 발생했습니다.", e);
        }
    }
    
    @Override
    @Transactional
    public void updateResume(ResumeVo resumeVo) {
        try {
            int resumeId = resumeVo.getResumeId();
            System.out.println(">>> [ResumeService] 이력서 업데이트 시작, resumeId: " + resumeId);
            
            // 1. 메인 이력서 정보 업데이트
            resumeDao.updateResume(resumeVo);
            System.out.println(">>> [ResumeService] 메인 이력서 업데이트 완료");
            
            // 2. 기존 하위 데이터 모두 삭제
            deleteResumeDetails(resumeId);
            
            // 3. 새로운 하위 데이터 저장
            saveResumeDetails(resumeVo, resumeId);
            
            System.out.println(">>> [ResumeService] 전체 이력서 업데이트 완료");
            
        } catch (Exception e) {
            System.err.println(">>> [ResumeService] 이력서 업데이트 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("이력서 업데이트 중 오류가 발생했습니다.", e);
        }
    }
    
    @Override
    @Transactional
    public void saveResumeWithDetails(ResumeVo resumeVo) {
        // saveResume와 동일한 로직 (별칭 메서드)
        saveResume(resumeVo);
    }
    
    // 이력서 하위 데이터 저장 공통 메서드
    private void saveResumeDetails(ResumeVo resumeVo, int resumeId) {
        // 2. 학력 정보 저장
        if (resumeVo.getSchoolList() != null && !resumeVo.getSchoolList().isEmpty()) {
            for (SchoolVo school : resumeVo.getSchoolList()) {
                if (isValidSchool(school)) {
                    school.setResumeId(resumeId);
                    resumeDao.insertSchool(school);
                    System.out.println(">>> [ResumeService] 학력 정보 저장: " + school.getSchoolName());
                }
            }
        }
        
        // 3. 경력 정보 저장
        if (resumeVo.getCareerList() != null && !resumeVo.getCareerList().isEmpty()) {
            for (CareerVo career : resumeVo.getCareerList()) {
                if (isValidCareer(career)) {
                    career.setResumeId(resumeId);
                    resumeDao.insertCareer(career);
                    System.out.println(">>> [ResumeService] 경력 정보 저장: " + career.getCompanyName());
                }
            }
        }
        
        // 4. 교육 정보 저장
        if (resumeVo.getEducationList() != null && !resumeVo.getEducationList().isEmpty()) {
            for (EducationVo education : resumeVo.getEducationList()) {
                if (isValidEducation(education)) {
                    education.setResumeId(resumeId);
                    resumeDao.insertEducation(education);
                    System.out.println(">>> [ResumeService] 교육 정보 저장: " + education.getEduName());
                }
            }
        }
        
        // 5. 자격증 정보 저장
        if (resumeVo.getCertificateList() != null && !resumeVo.getCertificateList().isEmpty()) {
            for (CertificateResumeVo certificate : resumeVo.getCertificateList()) {
                if (isValidCertificate(certificate)) {
                    certificate.setResumeId(resumeId);
                    resumeDao.insertCertificateResume(certificate);
                    System.out.println(">>> [ResumeService] 자격증 정보 저장: " + certificate.getCertificateName());
                }
            }
        }
        
        // 6. 포트폴리오 정보 저장
        if (resumeVo.getPortfolioList() != null && !resumeVo.getPortfolioList().isEmpty()) {
            for (PortfolioVo portfolio : resumeVo.getPortfolioList()) {
                if (isValidPortfolio(portfolio)) {
                    portfolio.setResumeId(resumeId);
                    resumeDao.insertPortfolio(portfolio);
                    System.out.println(">>> [ResumeService] 포트폴리오 저장: " + portfolio.getFileName());
                }
            }
        }
        
        // 7. 태그 정보 저장
        if (resumeVo.getTagIds() != null && !resumeVo.getTagIds().isEmpty()) {
            for (Long tagId : resumeVo.getTagIds()) {
                if (tagId != null && tagId > 0) {
                    resumeDao.insertResumeTag(resumeId, tagId);
                    System.out.println(">>> [ResumeService] 태그 정보 저장: " + tagId);
                }
            }
        }
    }
    
    // 이력서 하위 데이터 삭제 공통 메서드
    private void deleteResumeDetails(int resumeId) {
        System.out.println(">>> [ResumeService] 기존 하위 데이터 삭제 시작, resumeId: " + resumeId);
        
        resumeDao.deleteSchoolsByResumeId(resumeId);
        resumeDao.deleteCareersByResumeId(resumeId);
        resumeDao.deleteEducationsByResumeId(resumeId);
        resumeDao.deleteCertificatesByResumeId(resumeId);
        resumeDao.deletePortfoliosByResumeId(resumeId);
        resumeDao.deleteTagsByResumeId(resumeId);
        
        System.out.println(">>> [ResumeService] 기존 하위 데이터 삭제 완료");
    }
    
    // 유효성 검사 메서드들
    private boolean isValidSchool(SchoolVo school) {
        return school != null && 
               school.getSchoolName() != null && 
               !school.getSchoolName().trim().isEmpty();
    }
    
    private boolean isValidCareer(CareerVo career) {
        return career != null && 
               career.getCompanyName() != null && 
               !career.getCompanyName().trim().isEmpty();
    }
    
    private boolean isValidEducation(EducationVo education) {
        return education != null && 
               education.getEduName() != null && 
               !education.getEduName().trim().isEmpty();
    }
    
    private boolean isValidPortfolio(PortfolioVo portfolio) {
        return portfolio != null && 
               portfolio.getFileName() != null && 
               !portfolio.getFileName().trim().isEmpty();
    }
    
    private boolean isValidCertificate(CertificateResumeVo certificate) {
        return certificate != null && 
               certificate.getCertificateName() != null && 
               !certificate.getCertificateName().trim().isEmpty();
    }
}
