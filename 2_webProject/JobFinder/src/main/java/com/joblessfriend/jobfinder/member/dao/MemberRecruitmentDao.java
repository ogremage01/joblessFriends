package com.joblessfriend.jobfinder.member.dao;

import com.joblessfriend.jobfinder.recruitment.domain.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;


public interface MemberRecruitmentDao {

	List<RecruitmentVo> selectRecruitmentList(int memberId);


}
