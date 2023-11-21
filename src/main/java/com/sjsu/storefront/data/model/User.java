package com.sjsu.storefront.data.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sjsu.storefront.common.UserType;
import com.sjsu.storefront.data.model.DTO.UserDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

//@JsonIgnoreProperties({"address","cart","payment_info","orders"})
@JsonIgnoreProperties({"cart","orders"})
@Entity
@Table(name = "users")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	private String firstName;
	private String lastName;
	
	@Column(unique = true)
	private String email; //email is the userId
	
	private String password;
	private String phone;

    @Enumerated(EnumType.STRING)
    private UserType userType;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private ShoppingCart cart;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_info_id")
    private PaymentInfo payment_info;
	
	 // Define the one-to-many relationship
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) //is this line correct with the mappedBy = "item" or should it be something else
    private List<Order> orders;
	
	//default constructor needed for JPA
	public User() {
		
	}
	
	public User(UserDTO usrDto) {
		this.id = usrDto.getId();
		this.firstName = usrDto.getFirstName();
		this.lastName = usrDto.getLastName();
		this.email = usrDto.getEmail();
		this.password = usrDto.getPassword();
		this.phone = usrDto.getPhone();
		this.userType = usrDto.getUserType();
	}

	public User(long id, String firstName, String lastName, String email, String password, String phone,
			UserType userType, Address address, ShoppingCart cart, PaymentInfo payment_info, List<Order> orders) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.userType = userType;
		this.address = address;
		this.cart = cart;
		this.payment_info = payment_info;
		this.orders = orders;
	}




	//copy another user object into this user
	public void set(UserDTO user) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.phone = user.getPhone();
		this.userType = user.getUserType();
	}

	public long getId() {
		return id;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public ShoppingCart getCart() {
		return cart;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}

	public PaymentInfo getPayment_info() {
		return payment_info;
	}

	public void setPayment_info(PaymentInfo payment_info) {
		this.payment_info = payment_info;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", phone=" + phone + ", userType=" + userType + ", address=" + address
				+ ", cart=" + cart + ", payment_info=" + payment_info + ", orders=" + orders + "]";
	}

}
