package com.joblessfriend.jobfinder.profiletemp.dao;

import java.util.List;

import com.joblessfriend.jobfinder.profiletemp.domain.ProfileTempVo;

public interface ProfileTempDao {
	void insertProfileTemp(ProfileTempVo vo);
    List<ProfileTempVo> findByMemberId(int memberId);
    void deleteByMemberId(int memberId);

}
