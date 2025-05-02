package com.joblessfriend.jobfinder.admin.service;


import com.joblessfriend.jobfinder.admin.domain.AdminVo;


public interface AdminService {

	
	public AdminVo adminExist(String adminId, String password);
	
}
