package com.joblessfriend.jobfinder.community.dao;

import java.util.List;

import com.joblessfriend.jobfinder.community.domain.PostCommentVo;

public interface ReplyDao {

	List<PostCommentVo> replySelectList(int postCommentId);

}
