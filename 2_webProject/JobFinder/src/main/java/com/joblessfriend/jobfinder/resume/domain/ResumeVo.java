package com.joblessfriend.jobfinder.resume.domain;

import java.time.LocalDate;

public class ResumeVo {

	    private int resumeId;
	    private String name;
	    private LocalDate birthdate;
	    private String phoneNumber;
	    private String email;
	    private String address;
	    private String selfIntroduction;
	    private int memberId;
	    private String profile;
	    
		public ResumeVo(int resumeId, String name, LocalDate birthdate, String phoneNumber, String email,
				String address, String selfIntroduction, int memberId, String profile) {
			super();
			this.resumeId = resumeId;
			this.name = name;
			this.birthdate = birthdate;
			this.phoneNumber = phoneNumber;
			this.email = email;
			this.address = address;
			this.selfIntroduction = selfIntroduction;
			this.memberId = memberId;
			this.profile = profile;
		}

		public int getResumeId() {
			return resumeId;
		}

		public void setResumeId(int resumeId) {
			this.resumeId = resumeId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public LocalDate getBirthdate() {
			return birthdate;
		}

		public void setBirthdate(LocalDate birthdate) {
			this.birthdate = birthdate;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getSelfIntroduction() {
			return selfIntroduction;
		}

		public void setSelfIntroduction(String selfIntroduction) {
			this.selfIntroduction = selfIntroduction;
		}

		public int getMemberId() {
			return memberId;
		}

		public void setMemberId(int memberId) {
			this.memberId = memberId;
		}

		public String getProfile() {
			return profile;
		}

		public void setProfile(String profile) {
			this.profile = profile;
		}

		@Override
		public String toString() {
			return "ResumeVo [resumeId=" + resumeId + ", name=" + name + ", birthdate=" + birthdate + ", phoneNumber="
					+ phoneNumber + ", email=" + email + ", address=" + address + ", selfIntroduction="
					+ selfIntroduction + ", memberId=" + memberId + ", profile=" + profile + "]";
		}
	    
		
	    

	    // getter, setter
	    
}
