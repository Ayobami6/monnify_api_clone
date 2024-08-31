package com.ayo.monnify_api_clone.auth;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.user.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;


    // implement getSigning key
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // implement extractAll claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(
        Map<String, Object>extractClaims,
        UserDetails userDetails
    ) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(getSigningKey())
                .compact();

    }

    public String generateTokenAPI(
        Map<String, Object>extractClaims,
        UserEntity user
    ) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(user.getApiKey())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(getSigningKey())
                .compact();

    }

    // get user apikey from claim
    public String getUserApiKey(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    public String getUserEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateTokenAPI(UserEntity user) {
        return generateTokenAPI(new HashMap<>(), user);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return getUserEmail(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    public boolean validateTokenAPI(String token, UserEntity user) {
        return getUserApiKey(token).equals(user.getApiKey()) &&!isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

}
