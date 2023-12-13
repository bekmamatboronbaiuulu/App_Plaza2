package peaksoft.app_plaza2.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    //@Value("${secrets.key}")
    @Value("java-7")
    private String secretsKey;
    private final Long TOKEN_EXPIRATION = 24 * 7 * 60 * 60 * 1000L; // 7 days

    public String createdToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, secretsKey)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createdToken(claims, userDetails.getUsername());
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretsKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> function) {
        final Claims claims = getAllClaimsFromToken(token);
        return function.apply(claims);
    }

    public Date getExpirationDataToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDataToken(token);
        return expiration.before(new Date());
    }
    public  String getUserNameFromToken(String token){
        return getClaimsFromToken(token,Claims::getSubject);
    }
    public  Boolean tokenIsValidation(String token,UserDetails userDetails){
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public String generateToken(String email){
        Map<String,Object> claims = new HashMap<>();
        return createdToken(claims,email);
    }
}
