package com.joblessfriend.jobfinder.admin.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.community.domain.PostCommentVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Repository
public class AdminCommunityCommentDaoImpl implements AdminCommunityCommentDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	String namespace = "com.joblessfriend.jobfinder.admin.dao.communityComment.";	
	
	@Override
	public List<PostCommentVo> commentSelectList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+"commentSelectList", searchVo);
	}
	
	@Override
	public int getCommentTotalCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace+"getCommentTotalCount", searchVo);
	}

	@Override
	public void commentDelete(List<Integer> commentIdList) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace+"replyByCommentDeleteList", commentIdList);//오류방지를 위한 자식 삭제
		sqlSession.delete(namespace+"commentDeleteList", commentIdList);
	}


}
