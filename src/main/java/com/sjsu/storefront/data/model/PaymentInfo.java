package com.sjsu.storefront.data.model;

import com.sjsu.storefront.common.CardType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "id")
    private Address billingAddress;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_info_id", referencedColumnName = "id")
    private PaymentInfo paymentInfo;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and setters
    private String cardNumber;
    private String expiry;
    private String CVV;
    private String nickName; //my VISA card or my ATM card etc.
    private CardType cardType;
    
    public PaymentInfo() {
    	
    }

	public PaymentInfo(Long id, Address billingAddress, PaymentInfo paymentInfo, String cardNumber, String expiry,
			String cVV, String nickName, CardType cardType) {
		super();
		this.id = id;
		this.billingAddress = billingAddress;
		this.paymentInfo = paymentInfo;
		this.cardNumber = cardNumber;
		this.expiry = expiry;
		CVV = cVV;
		this.nickName = nickName;
		this.cardType = cardType;
	}



	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getCVV() {
		return CVV;
	}

	public void setCVV(String cCV) {
		CVV = cCV;
	}

	public Long getId() {
		return id;
	}

	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	@Override
	public String toString() {
		return "PaymentInfo [id=" + id + ", billingAddress=" + billingAddress + ", paymentInfo=" + paymentInfo
				+ ", cardNumber=" + cardNumber + ", expiry=" + expiry + ", CVV=" + CVV + ", nickName=" + nickName
				+ ", cardType=" + cardType + "]";
	}
   
}

