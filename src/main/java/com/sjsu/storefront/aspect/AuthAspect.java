package com.sjsu.storefront.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sjsu.storefront.common.UnauthorizedException;
import com.sjsu.storefront.common.UserType;
import com.sjsu.storefront.web.ProductController;
import com.sjsu.storefront.web.UserSession;

import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class AuthAspect {

	private final HttpSession httpSession;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthAspect.class);


    @Autowired
    public AuthAspect(HttpSession httpSession) {
        this.httpSession = httpSession;
    }
	
    @Before("@annotation(com.sjsu.storefront.common.AuthZCheck)") // Pointcut for the AuthZCheck annotation
    public void checkAuthorization() {
        // Your authorization logic here
        UserSession userSession = (UserSession) httpSession.getAttribute("userSession");

        if (userSession == null) {
            throw new UnauthorizedException("User not logged in");
        }

        UserType userType = userSession.getUserType();
		logger.info("User Type is {} : ", userType);


        if (userType != UserType.ADMIN || userType != UserType.SUPER) {
            throw new UnauthorizedException("User not authorized");
        }
    }
    
    @Before("@annotation(com.sjsu.storefront.common.AuthNCheck)") // Pointcut for the AuthNCheck annotation
    public void checkAuthenticated() {
        // Your authorization logic here
        UserSession userSession = (UserSession) httpSession.getAttribute("userSession");

        if (userSession == null) {
            throw new UnauthorizedException("User not logged in");
        }
    }
}


