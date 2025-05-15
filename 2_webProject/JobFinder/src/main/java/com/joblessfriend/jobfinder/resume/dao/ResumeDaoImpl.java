package com.joblessfriend.jobfinder.resume.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.resume.domain.ResumeVo;

@Repository
public class ResumeDaoImpl implements ResumeDao{
	
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
        sqlSession.update(namespace + "updateProfileImage", param);
		
	}
}
