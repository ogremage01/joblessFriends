package com.joblessfriend.jobfinder.profiletemp.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.joblessfriend.jobfinder.profiletemp.domain.ProfileTempVo;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileTempDaoImpl implements ProfileTempDao{
	
	private final String namespace = "com.joblessfriend.jobfinder.profiletemp.mapper.ProfileTempMapper.";

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void insertProfileTemp(ProfileTempVo vo) {
        sqlSession.insert(namespace + "insertProfileTemp", vo);
    }

    @Override
    public List<ProfileTempVo> findByMemberId(int memberId) {
        return sqlSession.selectList(namespace + "findByMemberId", memberId);
    }

    @Override
    public void deleteByMemberId(int memberId) {
        sqlSession.delete(namespace + "deleteByMemberId", memberId);
    }

}
