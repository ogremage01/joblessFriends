package com.joblessfriend.jobfinder.resume.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joblessfriend.jobfinder.resume.domain.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<>();
        param.put("resumeId", resumeId);
        param.put("memberId", memberId);
        param.put("imageUrl", imageUrl);
        sqlSession.update(namespace + ".updateProfileImage", param);
		
	}

    @Override
    public ResumeVo getResumeByResumeId(int resumeId) {
        return null;
    }

    @Override
    public void insertResume(ResumeVo resume) {

    }

    @Override
    public void insertSchool(SchoolVo school) {

    }

    @Override
    public void insertEducation(EducationVo education) {

    }

    @Override
    public void insertCareer(CareerVo career) {

    }

    @Override
    public void insertCertificateResume(int resumeId, Long certificateId) {

    }

    @Override
    public void insertResumeTag(int resumeId, Long tagId) {

    }

    @Override
    public void insertPortfolio(PortfolioVo portfolio) {

    }

}
