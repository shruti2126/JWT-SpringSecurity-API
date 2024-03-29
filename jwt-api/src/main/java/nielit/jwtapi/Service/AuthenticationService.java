package nielit.jwtapi.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import nielit.jwtapi.Entity.UserEntity;
import nielit.jwtapi.Repository.UserRepository;
import nielit.jwtapi.dto.AuthenticationResponse;

@Service
public class AuthenticationService {
	
	private final UserRepository repo;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationService(UserRepository repo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager manager) {
		this.repo = repo;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = manager;
	}

	public AuthenticationResponse register(UserEntity request) {
		UserEntity user = new UserEntity();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user = repo.save(user);
		String jwt = jwtService.generateJwtToken(user.getUsername());

		return new AuthenticationResponse(jwt);
	}
	
	public AuthenticationResponse authenticate(UserEntity request) {
		 authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
							request.getPassword()));

		String jwt = jwtService.generateJwtToken(request.getUsername());
		return new AuthenticationResponse(jwt);
	}
}
