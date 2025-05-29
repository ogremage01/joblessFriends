package com.joblessfriend.jobfinder.search.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.search.dao.SearchDao;
import com.joblessfriend.jobfinder.util.SearchVo;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;
	
	@Override
	public int getRecruitmentTotalCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return searchDao.getRecruitmentTotalCount(searchVo);
	}

	@Override
	public List<RecruitmentVo> recruitmentList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return searchDao.recruitmentList(searchVo);
	}

	@Override
	public int getCommunityTotalCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return searchDao.getCommunityTotalCount(searchVo);
	}

	@Override
	public List<CommunityVo> communityList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return searchDao.communityList(searchVo);
	}

}
