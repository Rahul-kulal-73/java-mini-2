package com.yourdomain.studyplatform.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// NOTE: @WebFilter("/*") annotation has been REMOVED
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // Do not create a session if one doesn't exist
        
        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Paths that do NOT require authentication
        boolean isPublicPath = path.equals("/login") || path.equals("/register") || path.startsWith("/css/");
        
        boolean isLoggedIn = (session != null && session.getAttribute("userId") != null);

        if (isLoggedIn || isPublicPath) {
            // User is logged in OR is accessing a public page. Allow access.
            chain.doFilter(request, response);
        } else {
            // User is NOT logged in and is accessing a protected page. Redirect to login.
            res.sendRedirect("login"); 
        }
    }

    // You can leave init() and destroy() empty
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
