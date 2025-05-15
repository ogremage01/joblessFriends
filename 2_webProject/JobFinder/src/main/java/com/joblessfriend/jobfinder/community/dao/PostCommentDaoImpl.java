package com.joblessfriend.jobfinder.community.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.community.domain.PostCommentVo;

@Repository
public class PostCommentDaoImpl implements PostCommentDao{
	@Autowired
	private SqlSession sqlSession;
	String namespace = "com.joblessfriend.jobfinder.communityPostComment.";

	@Override
	public List<PostCommentVo> postCommentSelectList(int communityId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+"postCommentSelectList",communityId);
	}

	@Override
	public void postCommentInsert(PostCommentVo postCommentVo) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace+"postCommentInsert", postCommentVo);
	}

	@Override
	public void postCommentUpdate(PostCommentVo postCommentVo) {
		// TODO Auto-generated method stub
		sqlSession.update(namespace+"postCommentUpdate", postCommentVo);
	}

	@Override
	public void postCommentDelete(int postCommentId) {
		sqlSession.delete(namespace+"postCommentDelete", postCommentId);
	}

	
	
}
