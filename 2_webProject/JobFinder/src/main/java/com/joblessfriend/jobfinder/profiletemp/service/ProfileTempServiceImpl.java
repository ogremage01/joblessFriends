package com.joblessfriend.jobfinder.profiletemp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.joblessfriend.jobfinder.profiletemp.dao.ProfileTempDao;
import com.joblessfriend.jobfinder.profiletemp.domain.ProfileTempVo;
import org.springframework.stereotype.Service;

@Service
public class ProfileTempServiceImpl implements ProfileTempService{
	
	@Autowired
    private ProfileTempDao profileTempDao;

    @Override
    public void insertProfileTemp(ProfileTempVo vo) {
        profileTempDao.insertProfileTemp(vo);
    }

    @Override
    public List<ProfileTempVo> findByMemberId(int memberId) {
        return profileTempDao.findByMemberId(memberId);
    }

    @Override
    public void deleteByMemberId(int memberId) {
        profileTempDao.deleteByMemberId(memberId);
    }

}
