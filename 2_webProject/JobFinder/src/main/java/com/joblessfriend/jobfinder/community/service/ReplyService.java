package com.joblessfriend.jobfinder.community.service;

import java.util.List;

import com.joblessfriend.jobfinder.community.domain.PostCommentVo;

public interface ReplyService {

	List<PostCommentVo> replySelectList(int postCommentId);

}
