package com.sjsu.storefront.data.model.DTO;

import com.sjsu.storefront.common.CardType;


public class PaymentInfoDTO {
    private Long id;
    
    private String cardNumber;
    private String expiry;
    private String CVV;
    private String nickName; //my VISA card or my ATM card etc.
    private CardType cardType;
    
    public PaymentInfoDTO() {
    	
    }

	public PaymentInfoDTO(Long id, String cardNumber, String expiry,
			String cVV, String nickName, CardType cardType) {
		super();
		this.id = id;
		this.cardNumber = cardNumber;
		this.expiry = expiry;
		CVV = cVV;
		this.nickName = nickName;
		this.cardType = cardType;
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

	@Override
	public String toString() {
		return "PaymentInfo [id=" + id +  ", cardNumber=" + cardNumber + ", expiry=" + expiry + ", CVV=" + CVV + ", nickName=" + nickName
				+ ", cardType=" + cardType + "]";
	}
   
}

