package com.yourdomain.studyplatform.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter; // ANNOTATION IS BACK
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*") // ANNOTATION IS BACK
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        
        String path = req.getRequestURI().substring(req.getContextPath().length());

        boolean isPublicPath = path.equals("/login") || path.equals("/register") || path.startsWith("/css/");
        boolean isLoggedIn = (session != null && session.getAttribute("userId") != null);

        if (isLoggedIn || isPublicPath) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect("login"); 
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
