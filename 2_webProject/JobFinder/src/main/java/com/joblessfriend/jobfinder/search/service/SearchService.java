package com.joblessfriend.jobfinder.search.service;

import java.util.List;

import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.util.SearchVo;

public interface SearchService {

	int getRecruitmentTotalCount(SearchVo searchVo);

	List<RecruitmentVo> recruitmentList(SearchVo searchVo);

	int getCommunityTotalCount(SearchVo searchVo);

	List<CommunityVo> communityList(SearchVo searchVo);

}
