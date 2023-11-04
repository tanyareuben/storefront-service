package com.sjsu.storefront.aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import jakarta.servlet.http.HttpSession;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

    @Bean
    public AuthAspect authAspect(HttpSession httpSession) {
        return new AuthAspect(httpSession);
    }
}


