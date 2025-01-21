package com.springboot.config;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TimeCheckingHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
            throws Exception {

//        System.out.println("TimeCheckingHandler.preHandle()");
//
//        // Only apply the time check to quiz-related paths
//        String servletPath = req.getServletPath();
//        if (servletPath.startsWith("/user/javaquiz")) {
//            // Get current hour
//            LocalDateTime ldt = LocalDateTime.now();
//            int hour = ldt.getHour();
//
//            // Check if the time is outside the allowed range (9 AM to 11 AM)
//            if (hour <= 9 || hour >= 16) {
//                // Forward to the timeout page
//            	resp.sendRedirect("/user/restrictime");
//                return false;
//            }
//        }
        // Allow the request to proceed
        return true;
    } 
}
