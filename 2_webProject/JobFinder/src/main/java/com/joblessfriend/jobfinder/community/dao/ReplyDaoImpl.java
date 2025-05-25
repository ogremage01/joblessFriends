package com.joblessfriend.jobfinder.community.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.community.domain.PostCommentVo;
import com.joblessfriend.jobfinder.community.domain.ReplyVo;

@Repository
public class ReplyDaoImpl implements ReplyDao{

	@Autowired
	private SqlSession sqlSession;
	String namespace = "com.joblessfriend.jobfinder.communityReply.";
	
	@Override
	public List<ReplyVo> replySelectList(int postCommentId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+"replySelectList", postCommentId);
	}

	@Override
	public void replyInsert(ReplyVo replyVo) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace+"replyInsertOne", replyVo);
	}

	@Override
	public void replyDelete(int replyId) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace+"replyDeleteOne", replyId);
	}

	@Override
	public void replyUpdate(ReplyVo replyVo) {
		// TODO Auto-generated method stub
		sqlSession.update(namespace+"replyUpadte", replyVo);
	}

	@Override
	public void replyCommentDelete(int postCommentId) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace+"replyCommentDelete", postCommentId);
	}

}
