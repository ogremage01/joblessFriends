package com.joblessfriend.jobfinder.resume.dao;

import java.util.List;

import com.joblessfriend.jobfinder.resume.domain.CertificateVo;

public interface CertificateDao {

	List<CertificateVo> certificateList();
	List<CertificateVo> certificateSelectListByResumeId(int resumeId);
	void certificateInsertOne(CertificateVo certificateVo);
	void certificateDeleteOne(int resumeId);
	List<CertificateVo> certificateSearchList(String certificateName);
	

}
