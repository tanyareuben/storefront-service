package com.sjsu.storefront.data.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sjsu.storefront.data.model.PaymentInfo;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {
    Optional<PaymentInfo> findByUserId(Long userId);

	Optional<PaymentInfo> getPaymentInfoById(Long id);
}

