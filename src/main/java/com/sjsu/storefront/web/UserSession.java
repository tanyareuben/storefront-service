package com.sjsu.storefront.web;

import com.sjsu.storefront.common.UserType;

public class UserSession {
    
	private Long userId;
    private String email;
    private UserType userType;
    
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
}

