package com.sjsu.storefront.data.model;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.storefront.common.ProductCategory;
import com.sjsu.storefront.data.model.DTO.ImageDTO;
import com.sjsu.storefront.data.model.DTO.ProductDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String description;
	private double price;
	private double weight;
	private int quantityInStock;
	
	@Enumerated(EnumType.STRING)  // This annotation specifies that the enum values should be stored as strings in the database
	private ProductCategory productCategory;
	
    // Define the one-to-many relationship
    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<Image>();
	
	public Product(){
		
	}

	public Product(long id, String name, String description, double price, double weight, int quantityInStock,
			ProductCategory productCategory, List<Image> images) {
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

	public Product(ProductDTO productDTO) {
		this.id = productDTO.getId();
		this.description = productDTO.getDescription();
		this.name = productDTO.getName();
		this.price = productDTO.getPrice();
		this.productCategory = productDTO.getProductCategory();
		this.quantityInStock = productDTO.getQuantityInStock();
		this.weight = productDTO.getWeight();
		for(ImageDTO img : productDTO.getImages()) {
			Image image = new Image(img, this);
			image.setProduct(this);
			this.addImage(image);
		}
	}

	//copy another Product object into this user
	public void set(ProductDTO item) {
		this.name = item.getName();
		this.description = item.getDescription();
		this.price = item.getPrice();
		this.weight = item.getWeight();
		this.quantityInStock = item.getQuantityInStock(); 
		this.productCategory = item.getProductCategory();
		for(ImageDTO img : item.getImages()) {
			Image image = new Image(img, this);
			image.setProduct(this);
			this.addImage(image);
		}
	}
	
	public void addImage(Image img) {
		images.add(img);
	}
	
	public void deleteImage(Long id) {
		for(Image img : images) {
			if(img.getId() == id) {
				images.remove(img);
				img.setProduct(null);
				break;
			}
		}
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

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
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
