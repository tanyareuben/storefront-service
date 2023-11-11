package com.sjsu.storefront.common;

public class NotAuthenticated extends Exception{
	public NotAuthenticated(String message) {
		super(message);
	}
}
