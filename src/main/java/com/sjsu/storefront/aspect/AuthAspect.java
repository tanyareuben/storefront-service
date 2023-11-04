package com.sjsu.storefront.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sjsu.storefront.common.UnauthorizedException;
import com.sjsu.storefront.common.UserType;
import com.sjsu.storefront.web.UserSession;

import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class AuthAspect {

	private final HttpSession httpSession;

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

        if (userType != UserType.ADMIN) {
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


