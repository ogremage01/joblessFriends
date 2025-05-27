package com.joblessfriend.jobfinder.admin.dao;

import java.util.List;

import com.joblessfriend.jobfinder.community.domain.PostCommentVo;
import com.joblessfriend.jobfinder.util.SearchVo;

public interface AdminCommunityCommentDao {

	int getCommentTotalCount(SearchVo searchVo);

	List<PostCommentVo> commentSelectList(SearchVo searchVo);

	void commentDelete(List<Integer> commentIdList);

}
