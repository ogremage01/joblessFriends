package com.joblessfriend.jobfinder.admin.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.joblessfriend.jobfinder.admin.dao.AdminDao;
import com.joblessfriend.jobfinder.admin.domain.AdminVo;

public class AdminServiceImpl implements AdminService{
	
	@Autowired
	public AdminDao adminDao;

	@Override
	public AdminVo adminExist(String adminId, String password) {
		// TODO Auto-generated method stub
		AdminVo adminVo = adminDao.adminExist(adminId,password);
		
		if (adminVo != null) {
			return adminVo;
		}
		
		return null;
	}

	
}
