package com.sjsu.storefront.web;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.storefront.common.OrderStatus;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.common.WorkflowException;
import com.sjsu.storefront.data.model.DTO.OrderDTO;
import com.sjsu.storefront.web.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
    private OrderService orderService;
	
	//TODO add SAMEUSER check
	@GetMapping("/user/{id}")
    public List<OrderDTO> getAllOrdersForUser(@PathVariable Long id) {
        return orderService.getAllOrdersForUser(id);
    }
	
	//TODO add SAMEUSER check or ADMIN
	@GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId) {
        try {
            OrderDTO order = orderService.getOderById(orderId);
            return (ResponseEntity.ok(order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
	
	//TODO add ADMIN auth
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatus status) {
        orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok("Order Updated" + id);
    }
    
    //TODO only if SAMEUSER and ADMIN
    @PutMapping("/{id}/cancel")
    public ResponseEntity<UUID> cancelOrder(@PathVariable Long id) {
        try {
            UUID orderId = orderService.cancelOrder(id);
            return (ResponseEntity.ok(orderId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }catch (WorkflowException e) {
        	//TODO add logging here
            return ResponseEntity.badRequest().build();
        }
            
    }

}
