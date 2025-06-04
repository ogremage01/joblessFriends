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
	public int getRecruitmentSearchTotalCount(String keyword) {
		// TODO Auto-generated method stub
		return searchDao.getRecruitmentSearchTotalCount(keyword);
	}

	@Override
	public List<RecruitmentVo> getRecruitmentSearchList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return searchDao.getRecruitmentSearchList(searchVo);
	}

}
