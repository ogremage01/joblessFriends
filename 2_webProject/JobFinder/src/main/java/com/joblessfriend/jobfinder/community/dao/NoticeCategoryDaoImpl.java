package com.joblessfriend.jobfinder.community.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.community.domain.NoticeCategoryVo;

@Repository
public class NoticeCategoryDaoImpl implements NoticeCategoryDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	String namespace = "com.joblessfriend.jobfinder.community.noticeCategory.";

	@Override
	public List<NoticeCategoryVo> noticeCategoryList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+"noticeCategoryList");
	}
	
	
}
