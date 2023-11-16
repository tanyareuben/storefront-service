package com.sjsu.storefront.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@JsonIgnoreProperties({"product"})
@Entity
@Table(name = "images")
public class Image {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String imageLink;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	
	public Image()
	{
		
	}
	
	public Image(long id, String imageLink, Product product) {
		super();
		this.id = id;
		this.imageLink = imageLink;
		this.product = product;
	}



	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public long getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", imageLink=" + imageLink + ", product=" + product + "]";
	}

}
