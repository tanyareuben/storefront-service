package com.sjsu.storefront.data.model.DTO;

import com.sjsu.storefront.data.model.Image;

public class ImageDTO {
	private long id;
	private String imageLink;
	

	
	public ImageDTO()
	{
		
	}
	
	public ImageDTO(Image img)
	{
		this.id =img.getId();
		this.imageLink = img.getImageLink();
	}
	
	public ImageDTO(long id, String imageLink) {
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

	@Override
	public String toString() {
		return "Image [id=" + id + ", imageLink=" + imageLink + "]";
	}

}
