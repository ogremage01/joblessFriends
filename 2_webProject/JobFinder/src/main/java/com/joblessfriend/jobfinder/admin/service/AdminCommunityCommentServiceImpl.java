package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joblessfriend.jobfinder.admin.dao.AdminCommunityCommentDao;
import com.joblessfriend.jobfinder.community.domain.PostCommentVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Service
public class AdminCommunityCommentServiceImpl implements AdminCommunityCommentService{
	
	@Autowired
	private AdminCommunityCommentDao commentDao;
	
	@Override
	public int getCommentTotalCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return commentDao.getCommentTotalCount(searchVo);
	}

	@Override
	public List<PostCommentVo> commentSelectList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return commentDao.commentSelectList(searchVo);
	}

	@Override
	@Transactional
	public void commentDelete(List<Integer> commentIdList) {
		// TODO Auto-generated method stub
		commentDao.commentDelete(commentIdList);
	}

}
