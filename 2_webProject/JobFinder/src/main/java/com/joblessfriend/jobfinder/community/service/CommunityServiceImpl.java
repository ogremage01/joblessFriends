package com.joblessfriend.jobfinder.community.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.community.dao.CommunityDao;
import com.joblessfriend.jobfinder.community.domain.CommunityVo;

@Service
public class CommunityServiceImpl implements CommunityService{
	private Logger logger = LoggerFactory.getLogger(CommunityService.class);
	private final String logTitleMsg = "===================게시판 서비스 시작======================";
	
	@Autowired
	private CommunityDao communityDao;
	
	@Override
	public void communityInsertOne(CommunityVo communityVo){
		communityDao.communityInsertOne(communityVo);
		
	}

}
