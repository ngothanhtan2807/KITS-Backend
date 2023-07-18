package com.kits.ecommerce.services.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenService {
    @Value("${jwt.secret:123456}") // doc gia tri tu application.properties
    private String secretKey;

    @Value("${jwt.validity-in-ms}")
    private long validityInMilliseconds; // 1h

    @Autowired
    UserDetailsService userDetailsService;

    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
//		claims.put(username, claims);

        Date now = new Date();
        Date exp = new Date(now.getTime() + validityInMilliseconds);
        String accessToken = Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(exp)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();

        return accessToken;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getBody().getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Authentication getAuthentication(String username) {
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }
}
