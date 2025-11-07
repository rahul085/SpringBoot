package com.rahul.JwtPractise.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.Period;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final Key secretKey;

    public JwtUtil(@Value("${application.security.jwt.secret-key}") String secretKey){
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        if(keyBytes.length < 32){
            throw new IllegalArgumentException("Token should be at least 32  bytes long");
        }
        this.secretKey= Keys.hmacShaKeyFor(keyBytes);
    }

    @Value("${application.security.jwt.expirationMs}")
    private long expirationTime;


    public String generateActiveToken(UserDetails userDetails){
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Map<String, Object> claims=new HashMap<>();
        claims.put("roles",authorities);
        claims.put("tokenType","ActiveToken");
         return Jwts
                 .builder()
                 .setClaims(claims)
                 .setIssuedAt(Date.from(Instant.now()))
                 .setExpiration(Date.from(Instant.now().plusMillis(expirationTime)))
                 .setSubject(userDetails.getUsername())
                 .signWith(secretKey)
                 .compact();
    }

    public String generateRefreshToken(UserDetails userDetails){
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Map<String, Object> claims=new HashMap<>();
        claims.put("roles",authorities);
        claims.put("tokenType","RefreshToken");
         return Jwts
                 .builder()
                 .setClaims(claims)
                 .setIssuedAt(Date.from(Instant.now()))
                 .setExpiration(Date.from(Instant.now().plus(Period.ofDays(7))))
                 .setSubject(userDetails.getUsername())
                 .signWith(secretKey)
                 .compact();
    }

    public String extractUsername(String token){
        return extractAllClaims(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractAllClaims(token,Claims::getExpiration);
    }

    public String getTokenType(String token){
        return extractAllClaims(token,claims -> claims.get("tokenType",String.class));
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(Date.from(Instant.now()));
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username=extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);


    }








    private <T> T  extractAllClaims(String token, Function<Claims,T> claimsResolver){
        Claims claims=parseClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims parseClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }





}
