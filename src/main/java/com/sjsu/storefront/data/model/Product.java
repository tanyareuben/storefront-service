package com.sjsu.storefront.data.model;

import java.util.List;

import com.sjsu.storefront.common.ProductCategory;

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
@Table(name = "items")
public class Product {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String description;
	private double price;
	private double weight;
	private int quantityInStock;
	
	@Enumerated(EnumType.STRING)
	private ProductCategory category;
	
    // Define the one-to-many relationship
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;
	
	public Product()
	{
		
	}

	public Product(long id, String name, String description, double price, double weight, int quantityInStock,
			ProductCategory category, List<Image> images) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.weight = weight;
		this.quantityInStock = quantityInStock;
		this.category = category;
		this.images = images;
	}



	//copy another Product object into this user
	public void set(Product item) {
		this.name = item.name;
		this.description = item.description;
		this.price = item.price;
		this.weight = item.weight;
		this.quantityInStock = item.quantityInStock; 
		this.category = item.category;
		this.images = item.images;
	}
	
	public void addImage(Image img) {
		images.add(img);
	}
	
	public void deleteImage(Long id) {
		for(Image img : images) {
			if(img.getId() == id) {
				images.remove(img);
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

	public void setQuantityInStock(int quantityInStore) {
		this.quantityInStock = quantityInStore;
	}

	public long getId() {
		return id;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", weight="
				+ weight + ", quantityInStock=" + quantityInStock + "]";
	} 

	
}
