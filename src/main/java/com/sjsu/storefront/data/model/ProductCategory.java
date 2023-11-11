package com.sjsu.storefront.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;
    
    public ProductCategory() {
    	
    }

	public ProductCategory(Long id, String categoryName) {
		super();
		this.id = id;
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ProductCategory [id=" + id + ", categoryName=" + categoryName + "]";
	}
}
