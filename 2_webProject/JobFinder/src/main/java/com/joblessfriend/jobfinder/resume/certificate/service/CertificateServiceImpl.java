package com.joblessfriend.jobfinder.resume.certificate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.resume.certificate.dao.CertificateDao;
import com.joblessfriend.jobfinder.resume.certificate.domain.CertificateVo;

@Service
public class CertificateServiceImpl implements CertificateService{

	@Autowired	
	public CertificateDao certificateDao;
	
	@Override
	public List<CertificateVo> certificateSelectList(int resumeId) {
		// TODO Auto-generated method stub
		return certificateDao.certificateSelectList(resumeId);
	}

	@Override
	public void certificateInsertOne(CertificateVo certificateVo) {
		// TODO Auto-generated method stub
		certificateDao.certificateInsertOne(certificateVo);
	}

	@Override
	public void certificateDeleteOne(int certificateId, int resumeId) {
		// TODO Auto-generated method stub
		certificateDao.certificateDeleteOne(certificateId, resumeId);
	}

}
