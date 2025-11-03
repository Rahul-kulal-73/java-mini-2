package com.yourdomain.studyplatform.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}) // Apply to ALL paths
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // Do not create a session if one doesn't exist
        
        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Paths that do NOT require authentication
        boolean isPublicPath = path.equals("/login") || path.equals("/register") || path.equals("/");
        
        // Check if the user is authenticated
        if (session != null && session.getAttribute("userId") != null) {
            // User is logged in. Allow access.
            chain.doFilter(request, response);
        } else if (isPublicPath) {
            // User is NOT logged in but is accessing a public page. Allow access.
            chain.doFilter(request, response);
        } else {
            // User is NOT logged in and is accessing a protected page. Redirect to login.
            res.sendRedirect("login"); 
        }
    }
    // init() and destroy() methods can remain empty
}
