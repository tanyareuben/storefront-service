package com.sjsu.storefront.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "images")
public class Image {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String imageLink;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item items;
	
	public Image()
	{
		
	}
	
	public Image(long id, String imageLink) {
		super();
		this.id = id;
		this.imageLink = imageLink;
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

	public Item getItem() {
		return items;
	}

	public void setItem(Item item) {
		this.items = item;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", imageLink=" + imageLink + "]";
	}
	
	
}
