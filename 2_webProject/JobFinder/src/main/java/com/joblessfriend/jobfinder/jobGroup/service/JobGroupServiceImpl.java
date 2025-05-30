package com.joblessfriend.jobfinder.jobGroup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.jobGroup.dao.JobGroupDao;
import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;


@Service
public class JobGroupServiceImpl implements JobGroupService{
	
	@Autowired
	private JobGroupDao jobGroupDao;

	@Override
	public List<JobGroupVo> jobGroupSelectList(int page) {
		// TODO Auto-generated method stub
		return jobGroupDao.jobGroupSelectList(page);
	}

	@Override
	public List<JobGroupVo> jobGroupSelectList(int page, String keyword) {
		// TODO Auto-generated method stub
		return jobGroupDao.jobGroupSelectList(page,keyword);
	}

	@Override
	public int jobGroupCount(String keyword) {
		// TODO Auto-generated method stub
		return jobGroupDao.jobGroupCount(keyword);
	}

	@Override
	public int jobGroupCount() {
		// TODO Auto-generated method stub
		return jobGroupDao.jobGroupCount();
	}

	@Override
	public List<JobGroupVo> selectAllJobGroupsForAjax() {
		// TODO Auto-generated method stub
		return jobGroupDao.selectAllJobGroupsForAjax();
	}

	@Override
	public String getJobGroupNameById(int jobGroupId) {
		// TODO Auto-generated method stub
		System.out.println(">>>>> jobGroupId 전달됨: " + jobGroupId);
	    String name = jobGroupDao.getJobGroupNameById(jobGroupId);
	    System.out.println(">>>>> 가져온 직군이름: " + name);
	    return name;
	}


}
