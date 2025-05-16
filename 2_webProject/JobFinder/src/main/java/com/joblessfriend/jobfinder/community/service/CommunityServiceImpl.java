package com.joblessfriend.jobfinder.community.service;

import java.util.List;
import java.util.Map;

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

	@Override
	public List<CommunityVo> communitySelectList() {
		// TODO Auto-generated method stub
		return communityDao.communitySelectList();
	}

	@Override
	public CommunityVo communityDetail(int no) {
		// TODO Auto-generated method stub
		 CommunityVo community = communityDao.communityDetail(no);

		return community;
	}

	@Override
	public void communityUpdate(CommunityVo communityVo) {
		// TODO Auto-generated method stub
		communityDao.communityUpdate(communityVo);
	}

	@Override
	public void communityDelete(int communityId) {
		// TODO Auto-generated method stub
		communityDao.communityDelete(communityId);
	}

	@Override
	public void communityFileInsertOne(Map<String, Object> fileMap) {
		System.out.println("이미지 저장 로직 시작");
		// TODO Auto-generated method stub
		communityDao.communityFileInsertOne(fileMap);
	}

}
