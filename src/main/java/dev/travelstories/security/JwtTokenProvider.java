package dev.travelstories.security;


import dev.travelstories.exceptions.BadRequestException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

   @Value("${app.jwt-secret}")
   private String jwtSecret;

   @Value("${app.jwt-expiration-milliseconds}")
   private int jwtExpirationInMilliseconds;


   //   generate JWT-token
   public String generateToken(Authentication authentication) {

      Claims claims = Jwts.claims().setSubject(authentication.getName());
      claims.put("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

      String username = authentication.getName();
      Date currentDate = new Date();
      Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMilliseconds);

      String token = Jwts.builder()
         .setSubject(username)
         .setClaims(claims)
         .setIssuedAt(new Date())
         .setExpiration(expireDate)
         .signWith(SignatureAlgorithm.HS512, jwtSecret)
         .compact();

      return token;
   }

   //   get username from JWT-token
   public String getUsernameFromJwt(String token) {
      Claims claims = Jwts.parser()
         .setSigningKey(jwtSecret)
         .parseClaimsJws(token)
         .getBody();

      return claims.getSubject();
   }


   // validate JWT-token
   public boolean validateToken(String token) {
      try {
         Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
         return true;
      } catch (SignatureException exception) {
         throw new BadRequestException("Invalid JWT signature");
      } catch (MalformedJwtException exception) {
         throw new BadRequestException("Invalid JWT token");
      } catch (ExpiredJwtException exception) {
         throw new BadRequestException("Expired JWT token");
      } catch (UnsupportedJwtException exception) {
         throw new BadRequestException("Unsupported JWT token");
      } catch (IllegalArgumentException exception) {
         throw new BadRequestException("JWT claims string is empty.");
      }
   }
}
