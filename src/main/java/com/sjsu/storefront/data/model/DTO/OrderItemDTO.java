package com.sjsu.storefront.data.model.DTO;

import com.sjsu.storefront.data.model.OrderItem;

public class OrderItemDTO {

    
    private Long id;
    private ProductDTO product;
    private int quantity;
    
	public OrderItemDTO() {
	}
	public OrderItemDTO(OrderItem item) {
		this.id = item.getId();
		this.product = new ProductDTO(item.getProduct());
		this.quantity = item.getQuantity();
	}
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "OrderItemDTO [id=" + id + ", product=" + product + ", quantity=" + quantity + "]";
	}

 }
