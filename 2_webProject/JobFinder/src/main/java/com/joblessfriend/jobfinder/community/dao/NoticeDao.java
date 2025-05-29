package com.joblessfriend.jobfinder.community.dao;

import java.util.List;

import com.joblessfriend.jobfinder.community.domain.NoticeVo;
import com.joblessfriend.jobfinder.util.SearchVo;

public interface NoticeDao {

	int getNoticeTotalCount(SearchVo searchVo);

	List<NoticeVo> noticeSelectList(SearchVo searchVo);

	int noticeSeqNum();

	void noticeInsertOne(NoticeVo noticeVo);

	NoticeVo noticeDetail(int no);

	void noticeViewCount(NoticeVo noticeVo);

	void noticeUpdate(NoticeVo noticeVo);

	void noticeDelete(List<Integer> noticeIdList);

}
