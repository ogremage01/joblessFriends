package com.joblessfriend.jobfinder.admin.dao;

import com.joblessfriend.jobfinder.admin.domain.AdminVo;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao {

	public AdminVo adminExist(String adminId, String password);

}
