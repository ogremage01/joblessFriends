package com.joblessfriend.jobfinder.admin.service;

import com.joblessfriend.jobfinder.admin.domain.AdminVo;

public interface AdminSevice {

	
	public AdminVo adminExist(String adminId, String password);
	
}
