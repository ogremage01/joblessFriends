package com.joblessfriend.jobfinder.admin.service;

import com.joblessfriend.jobfinder.admin.dao.AdminRecruitmentDao;
import com.joblessfriend.jobfinder.recruitment.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AdminRecruitmentServiceImpl implements AdminRecruitmentService {

    @Autowired
    private AdminRecruitmentDao recruitmentDao;


	@Override
	@Transactional
	public void jobPostDelete(List<Integer> jobPostIdList) {
		// TODO Auto-generated method stub
		
		recruitmentDao.jobPostFileDelete(jobPostIdList);
		recruitmentDao.jobPostTagDelete(jobPostIdList);
		recruitmentDao.jobPostDelete(jobPostIdList);
		
	}

    @Override
	public List<RecruitmentVo> adminRecruitmentList() {
		// TODO Auto-generated method stub
		return recruitmentDao.adminRecruitmentList();
	}






}
