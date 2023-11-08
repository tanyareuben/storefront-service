package com.sjsu.storefront.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.data.model.PaymentInfo;
import com.sjsu.storefront.web.services.PaymentInfoService;

@RestController
@RequestMapping("/payment-info")
public class PaymentInfoController {

    @Autowired
    private PaymentInfoService paymentInfoService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<PaymentInfo> getPaymentInfoByUserId(@PathVariable Long userId) {
        
        try {
            PaymentInfo paymentInfo = paymentInfoService.getPaymentInfoByUserId(userId);
            return (ResponseEntity.ok(paymentInfo));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePaymentInfo(@PathVariable Long id, @RequestBody PaymentInfo paymentInfo) {
        
        try {
        	paymentInfoService.updatePaymentInfo(id, paymentInfo);
            return (ResponseEntity.ok("Updated"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

