package com.joblessfriend.jobfinder.company.dao;

import com.joblessfriend.jobfinder.company.domain.CompanyApplyVo;
import com.joblessfriend.jobfinder.util.SearchVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyApplyDaoImpl implements CompanyApplyDao {

    @Autowired
    SqlSession sqlSession;
    @Override
    public List<CompanyApplyVo> getApplyMemberList(SearchVo vo) {
        return sqlSession.selectList("com.joblessfriend.jobfinder.company.dao.CompanyApplyDao.getApplyMemberList", vo);

    }

    @Override
    public int getApplyMemberCount(SearchVo vo) {
        return sqlSession.selectOne("com.joblessfriend.jobfinder.company.dao.CompanyApplyDao.getApplyMemberCount", vo);

    }
}
