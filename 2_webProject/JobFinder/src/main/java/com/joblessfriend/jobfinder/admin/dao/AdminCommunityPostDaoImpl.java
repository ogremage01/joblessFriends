package com.joblessfriend.jobfinder.admin.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminCommunityPostDaoImpl implements AdminCommunityPostDao{

	@Autowired
	private SqlSession sqlSession;
	
	String namespace = "com.joblessfriend.jobfinder.admin.dao.communityPost.";
	
	@Override
	public void communityPostDelete(List<Integer> communityIdList) {
		// TODO Auto-generated method stub

		sqlSession.delete(namespace+"fileByCommunityPostDelete", communityIdList);	
		sqlSession.delete(namespace+"communityPostDelete", communityIdList); 
	}

}
