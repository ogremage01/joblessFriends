package com.joblessfriend.jobfinder.community.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeVo{
	private int noticeId;
	private String adminId;
	private int noticeCategoryId;
	private String title;
	private String content;
	private Date createAt;
	private Date modifiedAt;
	private int views;
	
	private NoticeCategoryVo noticeCategory;
}
