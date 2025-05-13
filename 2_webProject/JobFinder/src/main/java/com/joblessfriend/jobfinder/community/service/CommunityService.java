package com.joblessfriend.jobfinder.community.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.joblessfriend.jobfinder.community.domain.CommunityVo;

public interface CommunityService{

	void communityInsertOne(CommunityVo communityVo);

	List<CommunityVo> communitySelectList();

	CommunityVo communityDetail(int no);

	void communityUpdate(CommunityVo communityVo);

	void communityDelete(int communityId);
}
