package com.joblessfriend.jobfinder.community.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.community.dao.PostCommentDao;
import com.joblessfriend.jobfinder.community.domain.PostCommentVo;

@Service
public class PostCommentServiceImpl implements PostCommentService{
	private Logger logger = LoggerFactory.getLogger(PostCommentService.class);
	private final String logTitleMsg = "===================게시판-댓글 서비스 시작======================";
	
	@Autowired
	private PostCommentDao postCommentDao;

	@Override
	public List<PostCommentVo> postCommentSelectList(int communityId) {
		// TODO Auto-generated method stub
		return postCommentDao.postCommentSelectList(communityId);
	}

	@Override
	public void postCommentInsert(PostCommentVo postCommentVo) {
		// TODO Auto-generated method stub
		postCommentDao.postCommentInsert(postCommentVo);
	}

	@Override
	public void postCommentUpdate( PostCommentVo postCommentVo) {
		// TODO Auto-generated method stub
		postCommentDao.postCommentUpdate(postCommentVo);
	}
	
	@Override
	public void postCommentDelete(int postCommentId) {
		// TODO Auto-generated method stub
		postCommentDao.postCommentDelete(postCommentId);
	}

}
