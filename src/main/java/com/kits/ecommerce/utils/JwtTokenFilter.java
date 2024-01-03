package com.kits.ecommerce.utils;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.kits.ecommerce.services.impl.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Service
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {


        // doc token tu header
        String token = resolveToken(httpServletRequest);

        // verify token
        if (token != null) {
            // co token roi thi lay username, gọi db len user
            String username = jwtTokenService.getUsername(token);
            if (username != null) {
                Authentication auth = jwtTokenService.getAuthentication(username);
                // set vao context de co dang nhap roi
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    // lay token tu request gui len: header, params, form
    public String resolveToken(HttpServletRequest req) {
        // check postman header
        String bearerToken = req.getHeader("Authorization");
        System.out.println("sout here: "+ bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
