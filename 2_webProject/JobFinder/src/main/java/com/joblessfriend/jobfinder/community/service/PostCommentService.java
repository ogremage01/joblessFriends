package com.joblessfriend.jobfinder.community.service;

import java.util.List;

import com.joblessfriend.jobfinder.community.domain.PostCommentVo;

public interface PostCommentService {
	
	List<PostCommentVo> postCommentSelectList(int communityId);

	void postCommentInsert(PostCommentVo postCommentVo);
	
	void postCommentDelete(int postCommentId);

	void postCommentUpdate(PostCommentVo postCommentVo);
}
