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
	
	String namespace = "com.joblessfriend.jobfinder.search.";
	
	
	@Override
	public int getRecruitmentSearchTotalCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "getRecruitmentSearchTotalCount", searchVo);
	}

	@Override
	public List<RecruitmentVo> getRecruitmentSearchList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "getRecruitmentSearchList", searchVo);
	}


}
