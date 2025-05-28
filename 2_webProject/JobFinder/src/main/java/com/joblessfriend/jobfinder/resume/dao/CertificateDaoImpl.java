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
	public List<CertificateVo> certificateList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "certificateSelectList");
	}

	@Override
	public List<CertificateVo> certificateSelectListByResumeId(int resumeId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "certificateSelectListByResumeId", resumeId);
	}

	@Override
	public void certificateInsertOne(CertificateVo certificateVo) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + "certificateInsertOne", certificateVo);
	}

	@Override
	public void certificateDeleteOne(int resumeId) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace + "certificateDeleteOne", resumeId);
	}

	@Override
	public List<CertificateVo> certificateSearchList(String certificateName) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "certificateSearchList", certificateName);
	}

	
}
