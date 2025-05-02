package com.joblessfriend.jobfinder.admin.domain;

public class AdminVo {
	
	private String adminId;
	private String password;
	
	public AdminVo() {
		super();
	}
	
	public AdminVo(String adminId, String password) {
		super();
		this.adminId = adminId;
		this.password = password;
	}

	public String getadminId() {
		return adminId;
	}

	public void setadminId(String adminId) {
		this.adminId = adminId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AdminVo [adminId=" + adminId + ", password=" + password + "]";
	}
	
	

}
