package com.joblessfriend.jobfinder.community.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentVo extends CommunityVo{
	private int postCommentId;
	private int communityId;
	private int memberId;
	private String content;
	private Date createAt;
	private Date modifiedAt;
	private int replyCount;
}
