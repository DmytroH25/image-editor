package com.example.imageeditor.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class JwtUtils {

  @Value("${image-editor.app.jwt.secret}")
  private String secretKey;

  @Value("${image-editor.app.jwt.expiration}")
  private long expiration;

  public String generateToken(Authentication authentication) {
    return Jwts.builder()
        .setSubject(authentication.getName())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public Claims extractClaims(String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (SignatureException | MalformedJwtException | ExpiredJwtException |
             UnsupportedJwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
