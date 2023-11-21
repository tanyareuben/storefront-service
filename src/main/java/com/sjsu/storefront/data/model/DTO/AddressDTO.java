package com.sjsu.storefront.data.model.DTO;

import com.sjsu.storefront.data.model.Address;

public class AddressDTO {

    private Long id;

    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    
    public AddressDTO() {
    }

    public AddressDTO(Address address) {
    	if(address != null) {
    		this.street = address.getStreet();
        	this.city = address.getCity();
        	this.state = address.getState();
        	this.zipCode = address.getZipCode();
        	this.country = address.getCountry();
    	}
    }


	public AddressDTO(Long id, String street, String city, String state, String zipCode, String country) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.country = country;
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


	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", state=" + state + ", zipCode="
				+ zipCode + ", country=" + country + "]";
	}

}
