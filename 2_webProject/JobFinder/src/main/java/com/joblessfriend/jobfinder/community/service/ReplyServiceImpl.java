package com.joblessfriend.jobfinder.community.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.community.dao.ReplyDao;
import com.joblessfriend.jobfinder.community.domain.PostCommentVo;
import com.joblessfriend.jobfinder.community.domain.ReplyVo;

@Service
public class ReplyServiceImpl implements ReplyService{
	private Logger logger = LoggerFactory.getLogger(PostCommentService.class);
	private final String logTitleMsg = "===================게시판-대댓글 서비스 시작======================";
	
	@Autowired
	private ReplyDao replyDao;
	
	@Override
	public List<ReplyVo> replySelectList(int postCommentId) {
		// TODO Auto-generated method stub
		return replyDao.replySelectList(postCommentId);
	}

	@Override
	public void replyInsert(ReplyVo replyVo) {
		// TODO Auto-generated method stub
		replyDao.replyInsert(replyVo);
	}

	@Override
	public void replyDelete(int replyId) {
		// TODO Auto-generated method stub
		replyDao.replyDelete(replyId);
	}

	@Override
	public void replyUpdate(ReplyVo replyVo) {
		// TODO Auto-generated method stub
		replyDao.replyUpdate(replyVo);
	}

	@Override
	public void replyCommentDelete(int postCommentId) {
		// TODO Auto-generated method stub
		replyDao.replyCommentDelete(postCommentId);
	}

}
