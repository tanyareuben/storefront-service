package com.sjsu.storefront.web.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.data.model.PaymentInfo;
import com.sjsu.storefront.data.respository.PaymentInfoRepository;

@Service
public class PaymentInfoService {

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    public PaymentInfo getPaymentInfoByUserId(Long userId) throws ResourceNotFoundException {
        return paymentInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentInfo not found for user with id " + userId));
    }
    
    
    public void updatePaymentInfo(Long id, PaymentInfo paymentInfo) throws ResourceNotFoundException {
        Optional<PaymentInfo> pInfo = paymentInfoRepository.getPaymentInfoById(id);
        
        PaymentInfo existingPaymentInfo = pInfo.get();
        // Update payment info properties here
        if(existingPaymentInfo != null) {
        	existingPaymentInfo.setCardNumber(paymentInfo.getCardNumber());
            existingPaymentInfo.setExpiry(paymentInfo.getExpiry());
            existingPaymentInfo.setCVV(paymentInfo.getCVV());
            paymentInfoRepository.save(existingPaymentInfo);
        }
        else {
        	throw new ResourceNotFoundException("PaymentInfo not found for {id}" + id );
        }
        
    }
}
