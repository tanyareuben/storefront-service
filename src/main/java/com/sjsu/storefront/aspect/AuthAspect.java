package com.sjsu.storefront.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sjsu.storefront.common.UnauthorizedException;
import com.sjsu.storefront.common.UserType;
import com.sjsu.storefront.web.UserSession;

import jakarta.servlet.http.HttpServletRequest;
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


        if (userType != UserType.ADMIN) { // || userType != UserType.SUPER)
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
    
    @Before("@annotation(com.sjsu.storefront.common.AuthSameUser)") // Pointcut for the SameUser annotation
    public void checkSameUser() {
        // Your authorization logic here
        UserSession userSession = (UserSession) httpSession.getAttribute("userSession");

        if (userSession == null) {
            throw new UnauthorizedException("User not logged in");
        }

        Long userIdFromSession = userSession.getUserId();

        // Access the userId from the URL path
        Long userIdFromPath = extractUserIdFromPath();

        if (!userIdFromSession.equals(userIdFromPath)) {
            throw new UnauthorizedException("User not authorized. The Resource you are accessing is not of the LoggedIn user");
        }
    }

    
    // Helper method to extract userId from the URL path
    private Long extractUserIdFromPath() {
        // Your logic to extract userId from the URL path
        // For example, using HttpServletRequest
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // Get the URI of the request
        String uri = request.getRequestURI();

        // Assuming userId is at a specific position in the path
        // Adjust the index based on your URL structure
        int userIdIndex = 2;

        // Split the URI into segments using "/"
        String[] pathSegments = uri.split("/");

        // Check if the URI contains the expected number of segments
        if (pathSegments.length > userIdIndex) {
            // Parse and return the userId
            return Long.parseLong(pathSegments[userIdIndex]);
        } else {
            throw new RuntimeException("Unable to extract userId from the URL path");
        }
    }


}


