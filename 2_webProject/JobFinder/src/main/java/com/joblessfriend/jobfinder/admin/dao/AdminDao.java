package com.joblessfriend.jobfinder.admin.dao;

import com.joblessfriend.jobfinder.admin.domain.AdminVo;

public interface AdminDao {

	AdminVo adminExist(String adminId, String password);

}
