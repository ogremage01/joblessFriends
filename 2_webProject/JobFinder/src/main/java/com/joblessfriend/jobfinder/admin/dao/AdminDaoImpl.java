package com.joblessfriend.jobfinder.admin.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.joblessfriend.jobfinder.admin.domain.AdminVo;

public class AdminDaoImpl implements AdminDao{
	
	
	@Autowired
	private SqlSession sqlSession;
	
	private String namespace = "com.joblessfriend.jobfinder";
	

	@Override
	public AdminVo adminExist(String adminId, String password) {
		// TODO Auto-generated method stub
		HashMap<String, String> paramMap = new HashMap<>();
		
		paramMap.put("adminId", adminId);
		paramMap.put("password", password);
		return sqlSession.selectOne(namespace + "adminExist", paramMap);
	}

}
