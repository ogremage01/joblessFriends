package com.joblessfriend.jobfinder.profiletemp.domain;

public class ProfileTempVo {
	private int memberId;
	private String imageUrl;
	
	public ProfileTempVo() {}
	
	public ProfileTempVo(int memberId, String imageUrl) {
		this.memberId = memberId;
		this.imageUrl = imageUrl;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "ProfileTempVo [memberId=" + memberId + ", imageUrl=" + imageUrl + "]";
	}	
}
