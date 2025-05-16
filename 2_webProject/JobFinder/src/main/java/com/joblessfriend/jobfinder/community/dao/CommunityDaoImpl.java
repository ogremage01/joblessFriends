package com.joblessfriend.jobfinder.community.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.community.domain.CommunityVo;


@Repository
public class CommunityDaoImpl implements CommunityDao {

	@Autowired
	private SqlSession sqlSession;
	
	String namespace = "com.joblessfriend.jobfinder.community.";
	
	@Override
	public void communityInsertOne(CommunityVo communityVo) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace+"communityInsertOne",communityVo);
		
	}

	@Override
	public List<CommunityVo> communitySelectList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+"communitySelectList");
	}

	@Override
	public CommunityVo communityDetail(int no) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace+"communitySelectOne",no);//(이동할 매퍼, 전달할 값)>>값 전달이 없을 경우, (이동할 매퍼)
	}

	@Override
	public void communityUpdate(CommunityVo communityVo) {
		// TODO Auto-generated method stub
		sqlSession.update(namespace+"communityUpdate", communityVo);
	}

	@Override
	public void communityDelete(int communityId) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace+"communityDelete", communityId);
	}

	@Override
	public void communityFileInsertOne(Map<String, Object> fileMap) {
		System.out.println("맵!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+fileMap);
		// TODO Auto-generated method stub
		sqlSession.insert(namespace+"communityFileInsert", fileMap);
	}
	
	

}
