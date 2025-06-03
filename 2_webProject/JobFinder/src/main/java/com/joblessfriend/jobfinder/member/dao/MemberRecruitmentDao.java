package com.joblessfriend.jobfinder.member.dao;

import com.joblessfriend.jobfinder.member.domain.ApplyPostVo;
import com.joblessfriend.jobfinder.recruitment.domain.*;
import com.joblessfriend.jobfinder.util.SearchVo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;


public interface MemberRecruitmentDao {

	List<RecruitmentVo> selectRecruitmentList(int memberId, SearchVo searchVo);
	
	public void deleteOne(int memberId, int jobPostId);

	int bookmarkCount(int memberId, SearchVo searchVo);

	//찜 저장
	void bookMarkInsertOne(int memberId, int jobPostId);

	int applicationCount(int memberId, SearchVo searchVo);

	List<ApplyPostVo> selectApplicationList(int memberId, SearchVo searchVo);


}
