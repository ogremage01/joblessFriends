package com.joblessfriend.jobfinder.resume.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joblessfriend.jobfinder.resume.domain.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.skill.domain.SkillVo;

@Repository
class ResumeDaoImpl implements ResumeDao{
	
	private final String namespace = "com.joblessfriend.jobfinder.resume.dao.ResumeDao";

    @Autowired
    private SqlSession sqlSession;
    //채용공고 지원하기에서 이력서 출력용으로 사용예정 //
    @Override
    public List<ResumeVo> findResumesByMemberId(int memberId) {
        return sqlSession.selectList(namespace + ".findResumesByMemberId", memberId);
    }

    @Override
    public void deleteResumeById(int memberId, int resumeId) {
    	Map<String, Object> param = new HashMap<>();
    	param.put("memberId", memberId);
        param.put("resumeId", resumeId);
        sqlSession.delete(namespace + ".deleteResumeById", param);
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
    public ResumeVo getResumeByResumeCopyId(int resumeId) {
        return sqlSession.selectOne(namespace + ".getResumeByResumeCopyId", resumeId);
    }

    @Override
    public ResumeVo getResumeWithAllDetails(int resumeId) {
        System.out.println(">>> [ResumeDaoImpl] getResumeWithAllDetails 시작, resumeId: " + resumeId);
        
        try {
            // 메인 이력서 정보 조회
            
            ResumeVo resume = sqlSession.selectOne(namespace + ".getResumeByResumeId", resumeId);
            
            if (resume != null) {
               
                
                // 하위 데이터들 조회하여 설정
                
                List<SchoolVo> schools = getSchoolsByResumeId(resumeId);
                resume.setSchoolList(schools);
               
                List<CareerVo> careers = getCareersByResumeId(resumeId);
                resume.setCareerList(careers);
               
                List<EducationVo> educations = getEducationsByResumeId(resumeId);
                resume.setEducationList(educations);
               
                List<PortfolioVo> portfolios = getPortfoliosByResumeId(resumeId);
                resume.setPortfolioList(portfolios);
                
                List<CertificateResumeVo> certificateList = getCertificateByResumeId(resumeId);
                resume.setCertificateList(certificateList);
               
                List<SkillVo> skillList = getTagIdsByResumeId(resumeId);
                resume.setSkillList(skillList);
                
               
            } else {
                
            }
            
            return resume;
        } catch (Exception e) {
            
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ResumeVo getResumeCopyWithAllDetails(int resumeId) {
        System.out.println(">>> [ResumeDaoImpl] getResumeCopyWithAllDetails 시작, resumeId: " + resumeId);

        try {
            // 메인 이력서 정보 조회

            ResumeVo resume = sqlSession.selectOne(namespace + ".getResumeByResumeCopyId", resumeId);

            if (resume != null) {


                // 하위 데이터들 조회하여 설정

                List<SchoolVo> schools = getSchoolsByResumeId(resumeId);
                resume.setSchoolList(schools);

                List<CareerVo> careers = getCareersByResumeId(resumeId);
                resume.setCareerList(careers);

                List<EducationVo> educations = getEducationsByResumeId(resumeId);
                resume.setEducationList(educations);

                List<PortfolioVo> portfolios = getPortfoliosByResumeId(resumeId);
                resume.setPortfolioList(portfolios);

                List<CertificateResumeVo> certificateList = getCertificateByResumeId(resumeId);
                resume.setCertificateList(certificateList);

                List<SkillVo> skillList = getTagIdsByResumeId(resumeId);
                resume.setSkillList(skillList);


            } else {

            }

            return resume;
        } catch (Exception e) {

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
    public List<CertificateResumeVo> getCertificateByResumeId(int resumeId) {
        return sqlSession.selectList(namespace + ".getCertificateByResumeId", resumeId);
    }

    @Override
    public List<SkillVo> getTagIdsByResumeId(int resumeId) {
        return sqlSession.selectList(namespace + ".getTagIdsByResumeId", resumeId);
    }

	@Override
	public void insertResume(ResumeVo resumeVo) {
		
		sqlSession.insert(namespace + ".insertResume", resumeVo);
		
	}

    @Override
    public void updateResume(ResumeVo resumeVo) {
        System.out.println(">>> [ResumeDaoImpl] updateResume 호출됨, resumeId: " + resumeVo.getResumeId());
        sqlSession.update(namespace + ".updateResume", resumeVo);
        
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
	public void insertCertificateResume(CertificateResumeVo certificateResumeVo) {
		
	    sqlSession.insert(namespace + ".insertCertificateResume", certificateResumeVo);
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



    @Override
    public int selectCareerGradeScore(int careerJobYear) {
        // TODO Auto-generated method stub
        return sqlSession.selectOne(namespace + "selectCareerGradeScore", careerJobYear);

    }

}
