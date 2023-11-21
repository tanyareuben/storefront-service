package com.sjsu.storefront.data.model.DTO;

import com.sjsu.storefront.common.UserType;
import com.sjsu.storefront.data.model.Address;
import com.sjsu.storefront.data.model.User;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class MeDTO {
    private long id;
	
	private String firstName;
	private String lastName;
	
	private String email; //email is the userId
	
	private String password;
	private String phone;

    @Enumerated(EnumType.STRING)
    private UserType userType;
	
    private AddressDTO address;
		
    private PaymentInfoDTO payment_info;
	
	public MeDTO() {
		
	}
	
	public MeDTO(User usr) {
		this.id = usr.getId();
		this.email = usr.getEmail();
		this.firstName = usr.getFirstName();
		this.lastName = usr.getLastName();
		this.password = usr.getPassword();
		this.phone = usr.getPhone();
		this.userType = usr.getUserType();
		
		if(usr.getAddress() != null) {
			this.address = new AddressDTO(usr.getAddress());
		}
		if(usr.getPayment_info() != null) {
			this.payment_info = new PaymentInfoDTO(usr.getPayment_info());
		}
	}

	public MeDTO(long id, String firstName, String lastName, String email, String password, String phone,
			UserType userType, AddressDTO address, PaymentInfoDTO payment_info) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.userType = userType;
		this.address = address;
		this.payment_info = payment_info;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public PaymentInfoDTO getPayment_info() {
		return payment_info;
	}

	public void setPayment_info(PaymentInfoDTO payment_info) {
		this.payment_info = payment_info;
	}

	@Override
	public String toString() {
		return "meDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", phone=" + phone + ", userType=" + userType + ", address=" + address
				+ ", payment_info=" + payment_info + "]";
	}

}
