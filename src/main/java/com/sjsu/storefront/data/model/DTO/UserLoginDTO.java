package com.sjsu.storefront.data.model.DTO;

public class UserLoginDTO {
	
	private String email;
	private String password;
	
	public UserLoginDTO() {
		
	}
	
	public UserLoginDTO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
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
	@Override
	public String toString() {
		return "LoginDTO [email=" + email + ", password=" + password + "]";
	}
}
