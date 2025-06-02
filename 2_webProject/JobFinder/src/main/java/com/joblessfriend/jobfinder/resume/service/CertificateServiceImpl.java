package com.joblessfriend.jobfinder.resume.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.resume.dao.CertificateDao;
import com.joblessfriend.jobfinder.resume.domain.CertificateVo;

import lombok.RequiredArgsConstructor;

@Service
public class CertificateServiceImpl implements CertificateService{

	@Autowired	
	public CertificateDao certificateDao;
	
	@Override
	public List<CertificateVo> certificateList() {
		// TODO Auto-generated method stub
		return certificateDao.certificateList();
	}

	@Override
	public List<CertificateVo> certificateSelectListByResumeId(int resumeId) {
		// TODO Auto-generated method stub
		return certificateDao.certificateSelectListByResumeId(resumeId);
	}

	@Override
	public void certificateInsertOne(CertificateVo certificateVo) {
		// TODO Auto-generated method stub
		certificateDao.certificateInsertOne(certificateVo);
	}

	@Override
	public void certificateDeleteOne(int resumeId) {
		// TODO Auto-generated method stub
		certificateDao.certificateDeleteOne(resumeId);
	}

	@Override
	public List<CertificateVo> certificateSearchList(String certificateName) {
		// TODO Auto-generated method stub
		return certificateDao.certificateSearchList(certificateName);
	}


}
