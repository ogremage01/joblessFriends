package com.joblessfriend.jobfinder.admin.service;

import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.admin.domain.AdminVo;

@Service
public interface AdminService {

	
	public AdminVo adminExist(String adminId, String password);
	
}
