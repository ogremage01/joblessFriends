package com.joblessfriend.jobfinder.resume.dao;

import com.joblessfriend.jobfinder.recruitment.domain.JobPostAnswerVo;
import com.joblessfriend.jobfinder.recruitment.domain.JobPostQuestionVo;
import com.joblessfriend.jobfinder.resume.domain.*;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ResumeApplyDaoImpl implements ResumeApplyDao {

    @Autowired
    private SqlSession sqlSession;

    private final String namespace = "com.joblessfriend.jobfinder.resume.dao.ResumeApplyDao";

    @Override
    public void insertResumeCopy(ResumeVo resumeVo) {
        sqlSession.insert(namespace + ".insertResumeCopy", resumeVo);
    }

    @Override
    public void insertSchool(SchoolVo schoolVo) {
        sqlSession.insert(namespace + ".insertSchool", schoolVo);
    }

    @Override
    public void insertCareer(CareerVo careerVo) {
        sqlSession.insert(namespace + ".insertCareer", careerVo);
    }

    @Override
    public void insertEducation(EducationVo educationVo) {
        sqlSession.insert(namespace + ".insertEducation", educationVo);
    }

    @Override
    public void insertCertificateResume(CertificateResumeVo certificateVo) {
        sqlSession.insert(namespace + ".insertCertificateResume", certificateVo);
    }



    @Override
    public void insertPortfolio(PortfolioVo portfolioVo) {
        sqlSession.insert(namespace + ".insertPortfolio", portfolioVo);
    }
    @Override
    public List<Integer> getTagIdsByResumeId(int resumeId) {
        return sqlSession.selectList(namespace + ".getTagIdsByResumeId", resumeId);
    }

    @Override
    public void insertResumeTagCopy(int resumeId, int tagId) {
        sqlSession.insert(namespace + ".insertResumeTagCopy", Map.of(
                "resumeId", resumeId,
                "tagId", tagId
        ));
    }

    @Override
    public void insertResumeManage(ResumeManageVo manageVo) {
        sqlSession.insert(namespace + ".insertResumeManage", manageVo);
    }

    @Override
    public List<JobPostQuestionVo> findQuestionsByJobPostId(int jobPostId) {
        return sqlSession.selectList(namespace + ".findQuestionsByJobPostId", jobPostId);
    }

    @Override
    public int insertAnswers(List<JobPostAnswerVo> answerList) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("answerList", answerList);  // 이름 일치
        return sqlSession.insert(namespace + ".insertAnswers", paramMap);
    }

    @Override
    public int selectNextAnswerId() {
        return sqlSession.selectOne(namespace + ".selectNextAnswerId");
    }

    @Override
    public List<Integer> selectNextAnswerIds(int count) {
        return sqlSession.selectList(namespace + ".selectNextAnswerIds", count);
    }

    @Override
    public int countByMemberAndJobPost(int memberId, int jobPostId) {
        Map<String, Object> param = new HashMap<>();
        param.put("memberId", memberId);
        param.put("jobPostId", jobPostId);
        return sqlSession.selectOne(namespace + ".countByMemberAndJobPost", param);
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
               
                List<SkillVo> skillList = getTagListByResumeId(resumeId);
                resume.setSkillList(skillList);
                
               
            } else {
                
            }
            
            return resume;
        } catch (Exception e) {
            
            e.printStackTrace();
            throw e;
        }
    }

    private List<SkillVo> getTagListByResumeId(int resumeId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + ".getTagListByResumeId", resumeId);
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



    // getResumeWithAllDetails, getSchoolsByResumeId 등 조회 메서드는 필요 시 유지
}
