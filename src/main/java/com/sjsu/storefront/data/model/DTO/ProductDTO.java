package com.sjsu.storefront.data.model.DTO;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.storefront.common.ProductCategory;
import com.sjsu.storefront.data.model.Image;
import com.sjsu.storefront.data.model.Product;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ProductDTO {
	private long id;
	private String name;
	private String description;
	private double price;
	private double weight;
	private int quantityInStock;
	
	@Enumerated(EnumType.STRING)  // This annotation specifies that the enum values should be stored as strings in the database
	private ProductCategory productCategory;
	
    private List<ImageDTO> images = new ArrayList<ImageDTO>();
	
	public ProductDTO(){
		
	}

	public ProductDTO(Product prod) {
		this.id = prod.getId();
		this.name = prod.getName();
		this.description = prod.getDescription();
		this.price = prod.getPrice();
		this.weight = prod.getWeight();
		this.quantityInStock = prod.getQuantityInStock();
		this.productCategory = prod.getProductCategory();
		for(Image img : prod.getImages()) {
			this.images.add(new ImageDTO(img));
		}
	}
	
	public ProductDTO(long id, String name, String description, double price, double weight, int quantityInStock,
			ProductCategory productCategory, List<ImageDTO> images) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.weight = weight;
		this.quantityInStock = quantityInStock;
		this.productCategory = productCategory;
		this.images = images;
	}

	//copy another Product object into this user
	public void set(ProductDTO item) {
		this.name = item.name;
		this.description = item.description;
		this.price = item.price;
		this.weight = item.weight;
		this.quantityInStock = item.quantityInStock; 
		this.productCategory = item.productCategory;
		this.images = item.images;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<ImageDTO> getImages() {
		return images;
	}

	public void setImages(List<ImageDTO> images) {
		this.images = images;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", weight=" + weight + ", quantityInStock=" + quantityInStock + ", productCategory=" + productCategory + ", images="
				+ images + "]";
	}
	
}
