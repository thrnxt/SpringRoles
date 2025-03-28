package org.thr.crudrest;

import org.springframework.security.core.GrantedAuthority;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUtil {
    private final String SECRET_KEY = "mySuperSecretKeyThatIsAtLeast32CharactersLong"; // Лучше вынести в application.properties

public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
    List<String> roles = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role) // Добавляем префикс
            .collect(Collectors.toList());

    return Jwts.builder()
            .setSubject(username)
            .claim("roles", roles)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
            .compact();
}
}
