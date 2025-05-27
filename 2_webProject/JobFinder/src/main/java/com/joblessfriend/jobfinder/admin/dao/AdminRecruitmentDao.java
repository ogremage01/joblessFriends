package com.joblessfriend.jobfinder.admin.dao;

import com.joblessfriend.jobfinder.recruitment.domain.*;
import com.joblessfriend.jobfinder.util.SearchVo;

import java.util.List;


public interface AdminRecruitmentDao {

	void jobPostDelete(List<Integer> jobPostIdList);//공고 삭제
	void jobPostFileDelete(List<Integer> jobPostIdList);//공고첨부파일삭제
	void jobPostTagDelete(List<Integer> jobPostIdList);//공고태그삭제

    List<RecruitmentVo> adminRecruitmentList(SearchVo searchVo);
	int getRecruitmentTotalCount(SearchVo searchVo);



}
