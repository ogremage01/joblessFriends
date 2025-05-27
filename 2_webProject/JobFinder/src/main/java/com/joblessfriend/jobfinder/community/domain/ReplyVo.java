package com.joblessfriend.jobfinder.community.domain;

import java.util.Date;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyVo extends PostCommentVo{
	
	@Id
	private int replyId;
	private int postCommentId;
	private int memberId;
	private String commentContent;
	private Date createAt;
	private Date modifiedAt;
}
