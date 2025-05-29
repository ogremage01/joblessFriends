package com.joblessfriend.jobfinder.search.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SqlSession sqlSession;
	
	String namespace = "com.joblessfreind.jobfinder.search.";
	
	
	@Override
	public int getRecruitmentTotalCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<RecruitmentVo> recruitmentList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCommunityTotalCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CommunityVo> communityList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
