package com.joblessfriend.jobfinder.resume.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joblessfriend.jobfinder.resume.domain.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.resume.domain.CareerVo;
import com.joblessfriend.jobfinder.resume.domain.EducationVo;
import com.joblessfriend.jobfinder.resume.domain.PortfolioVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.domain.SchoolVo;

@Repository
class ResumeDaoImpl implements ResumeDao{
	
	private final String namespace = "com.joblessfriend.jobfinder.resume.dao.ResumeDao";

    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<ResumeVo> findResumesByMemberId(int memberId) {
        return sqlSession.selectList(namespace + ".findResumesByMemberId", memberId);
    }

    @Override
    public void deleteResumeById(int memberId, int resumeId) {
        sqlSession.delete(namespace + ".deleteResumeById",
            new java.util.HashMap<String, Object>() {{
                put("memberId", memberId);
                put("resumeId", resumeId);
            }}
        );
    }

	@Override
	public void updateProfileImage(int resumeId, int memberId, String imageUrl) {
		Map<String, Object> param = new HashMap<>();
        param.put("resumeId", resumeId);
        param.put("memberId", memberId);
        param.put("imageUrl", imageUrl);
        sqlSession.update(namespace + ".updateProfileImage", param);
	}

	@Override
	public ResumeVo getResumeByResumeId(int resumeId) {
		return sqlSession.selectOne(namespace + ".getResumeByResumeId", resumeId);
	}

    @Override
    public ResumeVo getResumeWithAllDetails(int resumeId) {
        System.out.println(">>> [ResumeDaoImpl] getResumeWithAllDetails 시작, resumeId: " + resumeId);
        
        try {
            // 메인 이력서 정보 조회
            System.out.println(">>> [ResumeDaoImpl] 메인 이력서 정보 조회 시작...");
            ResumeVo resume = sqlSession.selectOne(namespace + ".getResumeByResumeId", resumeId);
            
            if (resume != null) {
                System.out.println(">>> [ResumeDaoImpl] 메인 이력서 조회 성공: " + resume.getMemberName());
                
                // 하위 데이터들 조회하여 설정
                System.out.println(">>> [ResumeDaoImpl] 하위 데이터 조회 시작...");
                
                System.out.println(">>> [ResumeDaoImpl] 학력 정보 조회...");
                List<SchoolVo> schools = getSchoolsByResumeId(resumeId);
                resume.setSchoolList(schools);
                System.out.println(">>> [ResumeDaoImpl] 학력 정보 " + schools.size() + "개 조회 완료");
                
                System.out.println(">>> [ResumeDaoImpl] 경력 정보 조회...");
                List<CareerVo> careers = getCareersByResumeId(resumeId);
                resume.setCareerList(careers);
                System.out.println(">>> [ResumeDaoImpl] 경력 정보 " + careers.size() + "개 조회 완료");
                
                System.out.println(">>> [ResumeDaoImpl] 교육 정보 조회...");
                List<EducationVo> educations = getEducationsByResumeId(resumeId);
                resume.setEducationList(educations);
                System.out.println(">>> [ResumeDaoImpl] 교육 정보 " + educations.size() + "개 조회 완료");
                
                System.out.println(">>> [ResumeDaoImpl] 포트폴리오 정보 조회...");
                List<PortfolioVo> portfolios = getPortfoliosByResumeId(resumeId);
                resume.setPortfolioList(portfolios);
                System.out.println(">>> [ResumeDaoImpl] 포트폴리오 정보 " + portfolios.size() + "개 조회 완료");
                
                // 자격증 ID 리스트를 CertificateVo 리스트로 변환
                System.out.println(">>> [ResumeDaoImpl] 자격증 정보 조회...");
                List<Integer> certificateIds = getCertificateIdsByResumeId(resumeId);
                List<CertificateVo> certificateList = new java.util.ArrayList<>();
                for (Integer certId : certificateIds) {
                    CertificateVo certVo = new CertificateVo();
                    certVo.setCertificateId(certId);
                    certificateList.add(certVo);
                }
                resume.setCertificateList(certificateList);
                System.out.println(">>> [ResumeDaoImpl] 자격증 정보 " + certificateList.size() + "개 조회 완료");
                
                System.out.println(">>> [ResumeDaoImpl] 모든 하위 데이터 조회 완료");
            } else {
                System.out.println(">>> [ResumeDaoImpl] 메인 이력서 정보를 찾을 수 없음, resumeId: " + resumeId);
            }
            
            return resume;
        } catch (Exception e) {
            System.err.println(">>> [ResumeDaoImpl] 이력서 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<SchoolVo> getSchoolsByResumeId(int resumeId) {
        return sqlSession.selectList(namespace + ".getSchoolsByResumeId", resumeId);
    }

    @Override
    public List<CareerVo> getCareersByResumeId(int resumeId) {
        return sqlSession.selectList(namespace + ".getCareersByResumeId", resumeId);
    }

    @Override
    public List<EducationVo> getEducationsByResumeId(int resumeId) {
        return sqlSession.selectList(namespace + ".getEducationsByResumeId", resumeId);
    }

    @Override
    public List<PortfolioVo> getPortfoliosByResumeId(int resumeId) {
        return sqlSession.selectList(namespace + ".getPortfoliosByResumeId", resumeId);
    }

    @Override
    public List<Integer> getCertificateIdsByResumeId(int resumeId) {
        return sqlSession.selectList(namespace + ".getCertificateIdsByResumeId", resumeId);
    }

	@Override
	public void insertResume(ResumeVo resumeVo) {
		System.out.println(">>> [ResumeDaoImpl] insertResume 호출됨");
		sqlSession.insert(namespace + ".insertResume", resumeVo);
		System.out.println(">>> [ResumeDaoImpl] insertResume SQL 실행 완료");
	}

    @Override
    public void updateResume(ResumeVo resumeVo) {
        System.out.println(">>> [ResumeDaoImpl] updateResume 호출됨, resumeId: " + resumeVo.getResumeId());
        sqlSession.update(namespace + ".updateResume", resumeVo);
        System.out.println(">>> [ResumeDaoImpl] updateResume SQL 실행 완료");
    }

	@Override
	public void insertSchool(SchoolVo school) {
		sqlSession.insert(namespace + ".insertSchool", school);
	}

    @Override
    public void updateSchool(SchoolVo school) {
        sqlSession.update(namespace + ".updateSchool", school);
    }

    @Override
    public void deleteSchoolsByResumeId(int resumeId) {
        sqlSession.delete(namespace + ".deleteSchoolsByResumeId", resumeId);
    }

	@Override
	public void insertEducation(EducationVo education) {
		sqlSession.insert(namespace + ".insertEducation", education);
	}

    @Override
    public void updateEducation(EducationVo education) {
        sqlSession.update(namespace + ".updateEducation", education);
    }

    @Override
    public void deleteEducationsByResumeId(int resumeId) {
        sqlSession.delete(namespace + ".deleteEducationsByResumeId", resumeId);
    }

	@Override
	public void insertCareer(CareerVo career) {
		sqlSession.insert(namespace + ".insertCareer", career);
	}

    @Override
    public void updateCareer(CareerVo career) {
        sqlSession.update(namespace + ".updateCareer", career);
    }

    @Override
    public void deleteCareersByResumeId(int resumeId) {
        sqlSession.delete(namespace + ".deleteCareersByResumeId", resumeId);
    }

	@Override
	public void insertCertificateResume(int resumeId, Long certificateId) {
		Map<String, Object> param = new HashMap<>();
	    param.put("resumeId", resumeId);
	    param.put("certificateId", certificateId);
	    sqlSession.insert(namespace + ".insertCertificateResume", param);
	}

    @Override
    public void deleteCertificatesByResumeId(int resumeId) {
        sqlSession.delete(namespace + ".deleteCertificatesByResumeId", resumeId);
    }

	@Override
	public void insertResumeTag(int resumeId, Long tagId) {
		Map<String, Object> param = new HashMap<>();
	    param.put("resumeId", resumeId);
	    param.put("tagId", tagId);
	    sqlSession.insert(namespace + ".insertResumeTag", param);
	}

    @Override
    public void deleteTagsByResumeId(int resumeId) {
        sqlSession.delete(namespace + ".deleteTagsByResumeId", resumeId);
    }

	@Override
	public void insertPortfolio(PortfolioVo portfolio) {
		sqlSession.insert(namespace + ".insertPortfolio", portfolio);
	}

    @Override
    public void updatePortfolio(PortfolioVo portfolio) {
        sqlSession.update(namespace + ".updatePortfolio", portfolio);
    }

    @Override
    public void deletePortfoliosByResumeId(int resumeId) {
        sqlSession.delete(namespace + ".deletePortfoliosByResumeId", resumeId);
    }

}
