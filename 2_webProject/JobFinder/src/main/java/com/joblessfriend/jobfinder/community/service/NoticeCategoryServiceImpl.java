package com.joblessfriend.jobfinder.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.community.dao.NoticeCategoryDao;
import com.joblessfriend.jobfinder.community.domain.NoticeCategoryVo;

@Service
public class NoticeCategoryServiceImpl implements NoticeCategoryService{
	@Autowired
	private NoticeCategoryDao noticeCategoryDao;

	@Override
	public List<NoticeCategoryVo> noticeCategoryList() {
		// TODO Auto-generated method stub
		return noticeCategoryDao.noticeCategoryList();
	}
	
	
}
