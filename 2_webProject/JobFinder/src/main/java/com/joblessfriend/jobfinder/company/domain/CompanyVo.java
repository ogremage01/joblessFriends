package com.joblessfriend.jobfinder.company.domain;

public class CompanyVo {
	
	private int companyId;
	private String email;
	private String password;
	private String companyName;
	private String brn;
	private String representative;
	private String tel;
	private int postalCodeId;
	private String arenaName;
	private String address;
	
	
	
	
	public CompanyVo() {
		super();
	}
	
	
	public CompanyVo(int companyId, String email, String password, String companyName, String brn,
			String representative, String tel, int postalCodeId, String arenaName, String address) {
		super();
		this.companyId = companyId;
		this.email = email;
		this.password = password;
		this.companyName = companyName;
		this.brn = brn;
		this.representative = representative;
		this.tel = tel;
		this.postalCodeId = postalCodeId;
		this.arenaName = arenaName;
		this.address = address;
	}


	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getBrn() {
		return brn;
	}
	public void setBrn(String brn) {
		this.brn = brn;
	}
	public String getRepresentative() {
		return representative;
	}
	public void setRepresentative(String representative) {
		this.representative = representative;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getPostalCodeId() {
		return postalCodeId;
	}
	public void setPostalCodeId(int postalCodeId) {
		this.postalCodeId = postalCodeId;
	}
	public String getArenaName() {
		return arenaName;
	}
	public void setArenaName(String arenaName) {
		this.arenaName = arenaName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "CompanyVo [companyId=" + companyId + ", Email=" + email + ", Password=" + password + ", companyName="
				+ companyName + ", brn=" + brn + ", representative=" + representative + ", tel=" + tel
				+ ", postalCodeId=" + postalCodeId + ", arenaName=" + arenaName + ", address=" + address + "]";
	}
	
	
	
	

}


