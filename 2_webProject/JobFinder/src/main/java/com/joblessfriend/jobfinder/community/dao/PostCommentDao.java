package com.joblessfriend.jobfinder.community.dao;

import java.util.List;

import com.joblessfriend.jobfinder.community.domain.PostCommentVo;

public interface PostCommentDao {
	List<PostCommentVo> postCommentSelectList(int communityId);

	void postCommentInsert(PostCommentVo postCommentVo);

	void postCommentUpdate(PostCommentVo postCommentVo);

	void postCommentDelete(int postCommentId);

}
