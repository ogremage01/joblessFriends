package com.joblessfriend.jobfinder.search.dao;

import java.util.List;

import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.util.SearchVo;

public interface SearchDao {

	int getRecruitmentSearchTotalCount(String keyword);

	List<RecruitmentVo> getRecruitmentSearchList(SearchVo searchVo);


}
