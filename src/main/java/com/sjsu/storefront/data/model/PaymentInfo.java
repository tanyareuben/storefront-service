package com.sjsu.storefront.data.model;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.storefront.common.CardType;
import com.sjsu.storefront.data.model.DTO.PaymentInfoDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and setters
    private String cardNumber;
    private String expiry;
    private String CVV;
    private String nickName; //my VISA card or my ATM card etc.
    private CardType cardType;
    
    @OneToMany(mappedBy = "paymentInfo")
    private List<Order> orders = new ArrayList<>();
    
    public PaymentInfo() {
    	
    }

	public PaymentInfo(Long id, User user, String cardNumber, String expiry, String cVV, String nickName,
			CardType cardType, List<Order> orders) {
		super();
		this.id = id;
		this.user = user;
		this.cardNumber = cardNumber;
		this.expiry = expiry;
		CVV = cVV;
		this.nickName = nickName;
		this.cardType = cardType;
		this.orders = orders;
	}



	public PaymentInfo(PaymentInfoDTO pInfo) {
		this.cardNumber = pInfo.getCardNumber();
		this.cardType = pInfo.getCardType();
		this.CVV = pInfo.getCVV();
		this.expiry = pInfo.getExpiry();
		this.id = pInfo.getId();
		this.nickName = pInfo.getNickName();
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
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "PaymentInfo [id=" + id + ", user=" + user + ", cardNumber=" + cardNumber + ", expiry=" + expiry
				+ ", CVV=" + CVV + ", nickName=" + nickName + ", cardType=" + cardType + ", orders=" + orders + "]";
	}

}

