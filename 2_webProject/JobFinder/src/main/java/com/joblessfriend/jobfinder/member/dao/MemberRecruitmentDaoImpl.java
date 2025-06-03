package com.joblessfriend.jobfinder.member.dao;

import com.joblessfriend.jobfinder.member.domain.ApplyPostVo;
import com.joblessfriend.jobfinder.recruitment.domain.*;
import com.joblessfriend.jobfinder.util.SearchVo;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberRecruitmentDaoImpl implements MemberRecruitmentDao {

    @Autowired
    private SqlSession sqlSession;

    String namespace = "com.joblessfriend.jobfinder.member.dao.RecruitmentDao";

    

	@Override
	public List<RecruitmentVo> selectRecruitmentList(int memberId, SearchVo searchVo) {
		// TODO Auto-generated method stub
		
		Map<String, Object> queryMap = new HashMap<>();
		
		queryMap.put("memberId", memberId);
		queryMap.put("searchVo", searchVo);
		
		return sqlSession.selectList(namespace+".selectRecruitmentList",queryMap);
	}
	
	
	@Override
	public void deleteOne(int memberId, int jobPostId) {
		// TODO Auto-generated method stub
		
		Map<String, Integer> bookmark = new HashMap<>();
		
		bookmark.put("memberId", memberId);
		bookmark.put("jobPostId", jobPostId);
		
		
		
		sqlSession.delete(namespace + ".deleteOne", bookmark);
		
	}


	@Override
	public int bookmarkCount(int memberId, SearchVo searchVo) {
		// TODO Auto-generated method stub
		Map<String, Object> bookmark = new HashMap<>();
			
			bookmark.put("memberId", memberId);
			bookmark.put("searchVo", searchVo);
		
		return sqlSession.selectOne(namespace + ".bookmarkCount", bookmark);
	}

//북마크 추가(찜 저장)
	@Override
	public void bookMarkInsertOne(int memberId, int jobPostId) {
		// TODO Auto-generated method stub
		Map<String, Object> queryMap = new HashMap<>();
		
		queryMap.put("memberId", memberId);
		queryMap.put("jobPostId", jobPostId);
		
		sqlSession.selectList(namespace+".bookMarkInsertOne",queryMap);
	}


	@Override
	public int applicationCount(int memberId, SearchVo searchVo) {
		// TODO Auto-generated method stub
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("memberId", memberId);
		queryMap.put("searchVo", searchVo);
		return sqlSession.selectOne(namespace + ".applicationCount", queryMap);
	}


	@Override
	public List<ApplyPostVo> selectApplicationList(int memberId, SearchVo searchVo) {
		// TODO Auto-generated method stub
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("memberId", memberId);
		queryMap.put("searchVo", searchVo);
		return sqlSession.selectList(namespace + ".selectApplicationList", queryMap);
	}

}
