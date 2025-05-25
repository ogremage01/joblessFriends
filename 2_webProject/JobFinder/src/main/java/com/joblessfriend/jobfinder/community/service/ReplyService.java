package com.joblessfriend.jobfinder.community.service;

import java.util.List;

import com.joblessfriend.jobfinder.community.domain.PostCommentVo;
import com.joblessfriend.jobfinder.community.domain.ReplyVo;

public interface ReplyService {

	List<ReplyVo> replySelectList(int postCommentId);

	void replyInsert(ReplyVo replyVo);

	void replyDelete(int replyId);

	void replyUpdate(ReplyVo replyVo);

	void replyCommentDelete(int postCommentId);

}
