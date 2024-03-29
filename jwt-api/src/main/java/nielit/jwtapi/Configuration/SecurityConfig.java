package nielit.jwtapi.Configuration;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import nielit.jwtapi.Filters.JwtAuthTokenFilter;
import nielit.jwtapi.Service.JwtService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    public static final SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public static final int TOKEN_EXPIRATION_TIME = 3600000;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public JwtAuthTokenFilter jwtAuthTokenFilter() {
        return new JwtAuthTokenFilter(userDetailsService, jwtService);
    }
    
    /**
     * Generates a SecurityFilterChain for JWT based authentication.
     *
     * @param (http) the HttpSecurity object
     * @return the SecurityFilterChain created
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF as it's not needed for JWT based authentication
                .csrf(csrf -> csrf.disable())
                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Use authorizeHttpRequests to configure request authorization
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/api/login").permitAll() //permit all these urls
                        .requestMatchers("/api/admin").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()) // Any other request must be authenticated
                        .addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Add JWT filter
        return http.build();
    }
    
    /**
     * Exposing authentication manager as a bean
     * 
     * @param authConfig the authentication configuration
     * @return the authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        try {
            return authConfig.getAuthenticationManager();
        } catch (Exception e) {
            // Handle the exception here
            throw new RuntimeException("Failed to get authentication manager", e);
        }
    }

   

}
