package com.joblessfriend.jobfinder.resume.certificate.dao;

import java.util.List;

import com.joblessfriend.jobfinder.resume.certificate.domain.CertificateVo;

public interface CertificateDao {

	List<CertificateVo> certificateSelectList(int resumeId);

	void certificateInsertOne(CertificateVo certificateVo);

	void certificateDeleteOne(int certificateId, int resumeId);

}
