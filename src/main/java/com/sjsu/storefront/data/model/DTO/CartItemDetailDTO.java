package com.sjsu.storefront.data.model.DTO;

import com.sjsu.storefront.data.model.CartItem;

public class CartItemDetailDTO {

    
    private Long id;
    private ProductDTO product;
    private int quantity;
    
	public CartItemDetailDTO() {
	}
	public CartItemDetailDTO(CartItem item) {
		this.id = item.getId();
		this.product = new ProductDTO(item.getProduct()); //new ProductDTO(item.getProduct());
		this.quantity = item.getQuantity();
	}
    
    
	public CartItemDetailDTO(Long id, ProductDTO product, int quantity) {
		super();
		this.id = id;
		this.product = product;
		this.quantity = quantity;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "CartItemDTO [id=" + id + ", product=" + product + ", quantity=" + quantity + "]";
	}
    
 }
