package nielit.jwtapi.Controller;

import nielit.jwtapi.Entity.UserEntity;
import nielit.jwtapi.Service.JwtService;
import nielit.jwtapi.dto.LoginRequest;
import nielit.jwtapi.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
	private JwtService jwtUtils;
	
	@GetMapping
	public String greetings() {
		return "Hello! I am running on port 8080";
	}
	
	/**
     * Authenticates the user using the provided login credentials.
     *
     * @param  loginRequest  the login credentials of the user
     * @return               the ResponseEntity containing the login response
     */
    @PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String jwt = jwtUtils.generateJwtToken(userDetails.getUsername());
		LoginResponse res = new LoginResponse(jwt, userDetails.getUsername());
		ResponseEntity<LoginResponse> response = ResponseEntity.ok(res);
		return response;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserEntity user) {
		return ResponseEntity.ok("User registered successfully");
	}		
	
}
