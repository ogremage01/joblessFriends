package com.joblessfriend.jobfinder.resume.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.resume.domain.EducationVo;

@Repository
public class EducationDaoImpl implements EducationDao {

	@Autowired
	private SqlSession sqlSession;
	
	String namespace = "com.joblessfreind.jobfinder.resume.education.";
	
	@Override
	public List<EducationVo> educationSelectList(int resumeId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "educationSelectList", resumeId);
	}

	@Override
	public void educationInsertOne(EducationVo educationVo) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + "educationSelectList", educationVo);
	}

	@Override
	public void educationDeleteOne(int eduId, int resumeId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		
		map.put("eduId", eduId);
		map.put("resumeId", resumeId);
		
		sqlSession.delete(namespace + "educationDeleteOne", map);
	}

}
