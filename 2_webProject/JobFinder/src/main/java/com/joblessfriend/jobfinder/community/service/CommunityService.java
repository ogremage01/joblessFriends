package com.joblessfriend.jobfinder.community.service;

import java.util.List;
import java.util.Map;


import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.util.SearchVo;

public interface CommunityService{

	void communityInsertOne(CommunityVo communityVo);

	List<CommunityVo> communitySelectList(SearchVo searchVo);

	CommunityVo communityDetail(int no);

	void communityUpdate(CommunityVo communityVo);

	void communityDelete(int communityId);

	void communityFileInsertOne(Map<String, Object> fileMap);

	int communitySeqNum();

	List<Map<String, Object>> communityFileList(int communityId);

	void communityFileDelete(int communityId);

	void communityFileNewInsert(Map<String, Object> fileMap);

	//페이지네이션 전체 페이지 수
	int getCommunityTotalCount(SearchVo searchVo);

}
