package com.joblessfriend.jobfinder.resume.service;

import java.util.List;

import com.joblessfriend.jobfinder.resume.domain.CertificateVo;

public interface CertificateService {
	public List<CertificateVo> certificateSelectList(int resumeId);
	
	public void certificateInsertOne(CertificateVo certificateVo);
	
	public void certificateDeleteOne(int certificateId, int resumeId);
}
