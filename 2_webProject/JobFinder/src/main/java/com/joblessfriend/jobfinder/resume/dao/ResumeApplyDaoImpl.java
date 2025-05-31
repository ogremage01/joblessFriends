package com.joblessfriend.jobfinder.resume.dao;

import com.joblessfriend.jobfinder.resume.domain.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ResumeApplyDaoImpl implements ResumeApplyDao {

    @Autowired
    private SqlSession sqlSession;

    private final String namespace = "com.joblessfriend.jobfinder.resume.dao.ResumeApplyDao";

    @Override
    public void insertResumeApply(ResumeVo resumeVo) {
        sqlSession.insert(namespace + ".insertResumeApply", resumeVo);
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
    public void insertCertificateResume(int resumeId, int certificateId) {
        var param = new java.util.HashMap<String, Object>();
        param.put("resumeId", resumeId);
        param.put("certificateId", certificateId);
        sqlSession.insert(namespace + ".insertCertificateResume", param);
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

    // getResumeWithAllDetails, getSchoolsByResumeId 등 조회 메서드는 필요 시 유지
}
