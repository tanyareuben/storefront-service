package com.sjsu.storefront.data.model;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.storefront.data.model.DTO.AddressDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Address {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    User user;
    
    @OneToMany(mappedBy = "shippingAddress")
    private List<Order> orders = new ArrayList<>();
    

    public Address() {
    }

    public Address(AddressDTO addr) {
    	this.street = addr.getStreet();
    	this.city = addr.getCity();
    	this.state = addr.getState();
    	this.zipCode = addr.getZipCode();
    	this.country = addr.getCountry();
    }

	public Address(Long id, String street, String city, String state, String zipCode, String country, User user) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.country = country;
		this.user = user;
	}


	public Long getId() {
		return id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", state=" + state + ", zipCode="
				+ zipCode + ", country=" + country + ", user=" + user + "]";
	}

}
