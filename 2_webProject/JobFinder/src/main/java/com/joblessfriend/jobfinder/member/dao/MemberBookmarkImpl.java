package com.joblessfriend.jobfinder.member.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberBookmarkImpl implements MemberBookmarkDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	String namespace = "com.joblessfriend.jobfinder.member.dao.MemberBookmarkDao";

	@Override
	public void deleteOne(int memberId, int jobPostId) {
		// TODO Auto-generated method stub
		
		Map<String, Integer> bookmark = new HashMap<>();
		
		bookmark.put("memberId", memberId);
		bookmark.put("jobPostId", jobPostId);
		
		
		
		sqlSession.delete(namespace + ".deleteOne", bookmark);
		
	}

}
