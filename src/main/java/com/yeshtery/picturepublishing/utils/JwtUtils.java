package com.yeshtery.picturepublishing.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtils {
    private String SECRET_KEY = "secret";

    @Value("${hoursAddedToJwtValidation}")
    private Integer HOUR_TO_JWT;

    @Value("${jwt.authorities.key}")
    private String AUTHORITIES_KEY;


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, HOUR_TO_JWT);
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder().claim(AUTHORITIES_KEY,authorities).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
