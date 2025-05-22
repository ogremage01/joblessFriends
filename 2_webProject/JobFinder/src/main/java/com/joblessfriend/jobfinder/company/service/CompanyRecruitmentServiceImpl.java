package com.joblessfriend.jobfinder.company.service;

import com.joblessfriend.jobfinder.company.dao.CompanyRecruitmentDao;
import com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao;
import com.joblessfriend.jobfinder.recruitment.domain.*;
import com.joblessfriend.jobfinder.skill.dao.SkillDao;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.util.SearchVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CompanyRecruitmentServiceImpl implements CompanyRecruitmentService {

    @Autowired
    private CompanyRecruitmentDao recruitmentDao;

    @Override
	@Transactional
	public void jobPostDelete(List<Integer> jobPostIdList) {
		// TODO Auto-generated method stub
		
		recruitmentDao.jobPostFileDelete(jobPostIdList);
		recruitmentDao.jobPostTagDelete(jobPostIdList);
		recruitmentDao.jobPostDelete(jobPostIdList);
		
	}


	@Override//회사용
	public void jobPostStop(List<Integer> jobPostIdList) {
		// TODO Auto-generated method stub
		recruitmentDao.jobPostStop(jobPostIdList);
		
	}


	@Override//회사용
	public List<CompanyRecruitmentVo> companyRecruitmentSelectList(int companyId) {
		// TODO Auto-generated method stub
		return recruitmentDao.companyRecruitmentSelectList(companyId);
	}




}
