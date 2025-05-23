package com.joblessfriend.jobfinder.resume.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.resume.domain.CertificateVo;

@Repository
public class CertificateDaoImpl implements CertificateDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	String namespace = "com.joblessfriend.jobfinder.resume.certificate.";

	@Override
	public List<CertificateVo> certificateSelectList(int resumeId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "certificateSelectList", resumeId);
	}

	@Override
	public void certificateInsertOne(CertificateVo certificateVo) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + "certificateInsertOne", certificateVo);
	}

	@Override
	public void certificateDeleteOne(int certificateId, int resumeId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		
		map.put("certificateId", certificateId);
		map.put("resumeId", resumeId);
		
		sqlSession.delete(namespace + "certificateDeleteOne", map);
	}
}
