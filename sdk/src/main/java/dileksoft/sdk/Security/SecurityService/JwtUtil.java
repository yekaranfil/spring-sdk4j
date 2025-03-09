package dileksoft.sdk.Security.SecurityService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {



    private static final String SECRET_KEY = "secret";

    // Kullanıcı adını token'dan çıkartmak için
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Expiration tarihini token'dan çıkartmak için
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Claims'ı çıkartmak için
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Token'dan claims'ı çıkartma
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // Token'ın süresinin dolup dolmadığını kontrol etme
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Token oluşturma
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    // Token'ı oluşturma
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // 10 saatlik bir süre
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Token'ı doğrulama
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Expiration tarihini milisaniye olarak almak
    public long extractExpirationMillis(String token) {
        return extractExpiration(token).getTime();
    }
}