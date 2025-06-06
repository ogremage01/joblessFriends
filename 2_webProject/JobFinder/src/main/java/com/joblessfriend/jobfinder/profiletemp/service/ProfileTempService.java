package com.joblessfriend.jobfinder.profiletemp.service;

import java.util.List;

import com.joblessfriend.jobfinder.profiletemp.domain.ProfileTempVo;

public interface ProfileTempService {
	
	void insertProfileTemp(ProfileTempVo vo);
    List<ProfileTempVo> findByMemberId(int memberId);
    void deleteByMemberId(int memberId);
    
    // 프로필 이미지 파일과 DB 데이터를 함께 삭제
    void deleteProfileImageAndData(String storedFileName, int memberId);

}
