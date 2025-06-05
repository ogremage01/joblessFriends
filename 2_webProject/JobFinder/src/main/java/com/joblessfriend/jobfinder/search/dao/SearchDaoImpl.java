package com.joblessfriend.jobfinder.search.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SqlSession sqlSession;

    String namespace = "com.joblessfriend.jobfinder.search.";

    @Override
    public int getRecruitmentSearchTotalCount(String keyword) {
        return sqlSession.selectOne(namespace + "getRecruitmentSearchTotalCount", keyword);
    }

    @Override
    public List<RecruitmentVo> getRecruitmentSearchList(SearchVo searchVo) {
        return sqlSession.selectList(namespace + "getRecruitmentSearchList", searchVo);
    }
}
