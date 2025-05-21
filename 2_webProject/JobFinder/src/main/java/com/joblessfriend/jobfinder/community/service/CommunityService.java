package com.joblessfriend.jobfinder.community.service;

import java.util.List;
import java.util.Map;


import com.joblessfriend.jobfinder.community.domain.CommunityVo;

public interface CommunityService{

	void communityInsertOne(CommunityVo communityVo);

	List<CommunityVo> communitySelectList();

	CommunityVo communityDetail(int no);

	void communityUpdate(CommunityVo communityVo);

	void communityDelete(int communityId);

	void communityFileInsertOne(Map<String, Object> fileMap);

	int communitySeqNum();

	List<Map<String, Object>> communityFileList(int communityId);

	void communityFileDelete(int communityId);

	void communityFileNewInsert(Map<String, Object> fileMap);

}
