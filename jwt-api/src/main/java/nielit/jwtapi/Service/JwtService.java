package nielit.jwtapi.Service;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import nielit.jwtapi.Configuration.SecurityConfig;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

@Service
public class JwtService {
    
    private SecretKey jwtSecret = SecurityConfig.SECRET;
    private int jwtExpirationMs = SecurityConfig.TOKEN_EXPIRATION_TIME;
   
    /**
     * Generates a JWT token for the given username.
     *
     * @param  username  the username for which the JWT token is generated
     * @return           the generated JWT token
     */
    public String generateJwtToken(String username) {
        System.out.println("GENERATING JWT TOKEN......");
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationMs)))
                .signWith(jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Retrieves the username from the JWT token.
     *
     * @param  token  the JWT token
     * @return       the username extracted from the JWT token
     */
    public String getUsernameFromJwtToken(String token) {
        System.out.println("GETTING USERNAME FROM JWT TOKEN......");
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret) 
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Validates a JWT token.
     *
     * @param  authToken  the JWT token to be validated
     * @return            true if the token is valid, false otherwise
     */
    public boolean validateJwtToken(String authToken) {
        System.out.println("VALIDATING JWT TOKEN ......");
        try {
            Jwts.parserBuilder()
                .setSigningKey(jwtSecret) // Convert secret string to Key
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }
}
