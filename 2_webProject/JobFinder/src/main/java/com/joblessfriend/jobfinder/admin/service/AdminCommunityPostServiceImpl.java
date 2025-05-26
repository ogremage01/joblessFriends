package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joblessfriend.jobfinder.admin.dao.AdminCommunityPostDao;

@Service
public class AdminCommunityPostServiceImpl implements AdminCommunityPostService{
	
	@Autowired
	private AdminCommunityPostDao adminCommunityPostDao;
	
	@Override
	@Transactional
	public void communityPostDelete(List<Integer> communityIdList) {
		// TODO Auto-generated method stub
		adminCommunityPostDao.communityPostDelete(communityIdList);
	}

}
