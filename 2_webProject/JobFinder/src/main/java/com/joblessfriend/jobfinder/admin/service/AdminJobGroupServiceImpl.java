package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.admin.dao.AdminJobGroupDao;
import com.joblessfriend.jobfinder.jobGroup.dao.JobGroupDao;
import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.util.SearchVo;


@Service
public class AdminJobGroupServiceImpl implements AdminJobGroupService{
	
	@Autowired
	private AdminJobGroupDao jobGroupDao;

	@Override
	public List<JobGroupVo> jobGroupSelectList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return jobGroupDao.jobGroupSelectList(searchVo);
	}

	@Override
	public int jobGroupCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return jobGroupDao.jobGroupCount(searchVo);
	}

	@Override
	public int insertJobGroup(String jobGroupName) {
		return jobGroupDao.insertJobGroup(jobGroupName);
	}

	@Override
	public int deleteJobGroups(List<Integer> jobGroupIdList) {
		return jobGroupDao.deleteJobGroups(jobGroupIdList);
	}

}
