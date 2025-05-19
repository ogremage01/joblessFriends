package com.joblessfriend.jobfinder.resume.certificate.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.resume.certificate.domain.CertificateVo;

@Repository
public class CertificateDaoImpl implements CertificateDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	String namespace = "com.joblessfriend.jobfinder.resume.certificate.";

	@Override
	public List<CertificateVo> certificateSelectList(int resumeId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "certificate", resumeId);
	}
}
