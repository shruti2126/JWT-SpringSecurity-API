package nielit.jwtapi.Controller;

import nielit.jwtapi.Entity.UserEntity;
import nielit.jwtapi.Service.CustomUserDetailsService;
import nielit.jwtapi.Service.JwtService;
import nielit.jwtapi.dto.AuthorizationResponse;
import nielit.jwtapi.dto.LoginRequest;
import nielit.jwtapi.dto.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
	private JwtService jwtUtils;
	
	@GetMapping
	public String greetings() {
		System.out.println("Hello! I am running on port 8080");
		return "Hello! I am running on port 8080";
	}
	
    @PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		// Authenticating the user
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword()));
		// Setting the authentication in the security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Getting UserDetails from Authentication object
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// Generating JWT token
		String jwt = jwtUtils.generateJwtToken(userDetails.getUsername());

		// Creating and returning the login response including the JWT token
		LoginResponse res = new LoginResponse(jwt, userDetails.getUsername());
		ResponseEntity<LoginResponse> response = ResponseEntity.ok(res);
		return response;
	}

	// @GetMapping("/admin")
    // public ResponseEntity<AuthorizationResponse> getProtectedResource() {
    //     // Add some logic here to fetch a resource.
       
	// 	return ResponseEntity.ok(new AuthorizationResponse(
	// 			SecurityContextHolder.getContext().getAuthentication().getName(),
	// 		"Authorized", true));
    // }

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserEntity user) {
		return ResponseEntity.ok("User registered successfully");
	}		
	
}
