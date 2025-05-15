package com.joblessfriend.jobfinder.profiletemp.service;

import java.util.List;

import com.joblessfriend.jobfinder.profiletemp.domain.ProfileTempVo;

public interface ProfileTempService {
	
	void insertProfileTemp(ProfileTempVo vo);
    List<ProfileTempVo> findByMemberId(int memberId);
    void deleteByMemberId(int memberId);

}
