package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import com.joblessfriend.jobfinder.community.domain.PostCommentVo;
import com.joblessfriend.jobfinder.util.SearchVo;

public interface AdminCommunityCommentService {

	int getCommentTotalCount(SearchVo searchVo);

	List<PostCommentVo> commentSelectList(SearchVo searchVo);

	void commentDelete(List<Integer> commentIdList);

}
