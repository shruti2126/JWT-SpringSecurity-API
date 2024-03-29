package nielit.jwtapi.Filters;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nielit.jwtapi.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;
    private JwtService jwtService;

    public JwtAuthTokenFilter(UserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }
    /**
     * Retrieves the JWT token from the provided HttpServletRequest.
     *
     * @param  request  the HttpServletRequest object containing the request information
     * @return          the JWT token extracted from the request header, or null if not found
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        System.out.println("GETTING JWT from Request.........");
        String bearerToken = request.getHeader("Authorization");
        System.out.println("bearerToken : " + bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
	 * This method performs a filter operation based on the provided HTTP request, response, and filter chain. 
	 * It attempts to extract a JWT from the request, validates it, and if valid, sets the user's authentication
	 * details in the security context. If an exception occurs during the process, it is logged.
	 *
	 * @param  request      the HTTP servlet request
	 * @param  response     the HTTP servlet response
	 * @param  filterChain  the filter chain for the request
	 * @throws ServletException  if a servlet exception occurs
	 * @throws IOException       if an I/O exception occurs
	 */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        try {
            String jwt = getJwtFromRequest(request);
            if (jwt != null && jwtService.validateJwtToken(jwt)) {
                String username = jwtService.getUsernameFromJwtToken(jwt);
                
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("USER DETAILS = " + userDetails.getAuthorities());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        filterChain.doFilter(request, response);
    }
}
