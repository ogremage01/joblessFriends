package com.joblessfriend.jobfinder.community.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.community.dao.PostCommentDao;
import com.joblessfriend.jobfinder.community.dao.ReplyDao;
import com.joblessfriend.jobfinder.community.domain.PostCommentVo;

@Service
public class ReplyServiceImpl implements ReplyService{
	private Logger logger = LoggerFactory.getLogger(PostCommentService.class);
	private final String logTitleMsg = "===================게시판-대댓글 서비스 시작======================";
	
	@Autowired
	private ReplyDao replyDao;
	
	@Override
	public List<PostCommentVo> replySelectList(int postCommentId) {
		// TODO Auto-generated method stub
		return replyDao.replySelectList(postCommentId);
	}

}
