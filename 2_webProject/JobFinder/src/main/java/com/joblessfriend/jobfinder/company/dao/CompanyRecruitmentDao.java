package com.joblessfriend.jobfinder.company.dao;

import com.joblessfriend.jobfinder.recruitment.domain.*;

import com.joblessfriend.jobfinder.util.SearchVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;


public interface CompanyRecruitmentDao {

	void jobPostDelete(List<Integer> jobPostIdList);//공고 삭제
	void jobPostFileDelete(List<Integer> jobPostIdList);//공고첨부파일삭제
	void jobPostTagDelete(List<Integer> jobPostIdList);//공고태그삭제


	List<CompanyRecruitmentVo> companyRecruitmentSelectList(int companyId);

	void jobPostStop(List<Integer> jobPostIdList);



}
