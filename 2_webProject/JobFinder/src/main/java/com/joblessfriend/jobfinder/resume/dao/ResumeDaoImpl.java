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
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<>();
        param.put("resumeId", resumeId);
        param.put("memberId", memberId);
        param.put("imageUrl", imageUrl);
        sqlSession.update(namespace + ".updateProfileImage", param);
		
	}


	@Override
	public ResumeVo getResumeByResumeId(int resumeId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".getResumeByResumeId", resumeId);
	}

	@Override
	public void insertResume(ResumeVo resume) {
		// TODO Auto-generated method stub
		System.out.println(">>> [ResumeDaoImpl] insertResume 호출됨");
		sqlSession.insert(namespace + ".insertResume", resume);
		System.out.println(">>> [ResumeDaoImpl] insertResume SQL 실행 완료");
		
	}

	@Override
	public void insertSchool(SchoolVo school) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".insertSchool", school);
		
	}

	@Override
	public void insertEducation(EducationVo education) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".insertEducation", education);
		
	}

	@Override
	public void insertCareer(CareerVo career) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".insertCareer", career);
		
	}

	@Override
	public void insertCertificateResume(int resumeId, Long certificateId) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<>();
	    param.put("resumeId", resumeId);
	    param.put("certificateId", certificateId);
	    sqlSession.insert(namespace + ".insertCertificateResume", param);
		
	}

	@Override
	public void insertResumeTag(int resumeId, Long tagId) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<>();
	    param.put("resumeId", resumeId);
	    param.put("tagId", tagId);
	    sqlSession.insert(namespace + ".insertResumeTag", param);
		
	}

	@Override
	public void insertPortfolio(PortfolioVo portfolio) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".insertPortfolio", portfolio);
		
	}

}
