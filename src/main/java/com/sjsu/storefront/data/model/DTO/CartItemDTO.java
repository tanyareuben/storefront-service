package com.sjsu.storefront.data.model.DTO;

import com.sjsu.storefront.data.model.CartItem;

public class CartItemDTO {

    
    private Long id;
    //private ProductDTO product;
    private Long productId;
    private int quantity;
    
	public CartItemDTO() {
	}
	public CartItemDTO(CartItem item) {
		this.id = item.getId();
		this.productId = item.getProduct().getId(); //new ProductDTO(item.getProduct());
		this.quantity = item.getQuantity();
	}
    
    
	public CartItemDTO(Long id, Long productId, int quantity) {
		super();
		this.id = id;
		this.productId = productId;
		this.quantity = quantity;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "CartItemDTO [id=" + id + ", productId=" + productId + ", quantity=" + quantity + "]";
	}
    
 }
