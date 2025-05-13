package com.joblessfriend.jobfinder.community.dao;

import java.util.List;

import com.joblessfriend.jobfinder.community.domain.CommunityVo;

public interface CommunityDao {

	void communityInsertOne(CommunityVo communityVo);
	
	List<CommunityVo> communitySelectList();

	CommunityVo communityDetail(int no);

	void communityUpdate(CommunityVo communityVo);

}
