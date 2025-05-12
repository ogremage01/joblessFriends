package com.joblessfriend.jobfinder.community.dao;


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

}
