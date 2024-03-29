package nielit.jwtapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorizationResponse {
	private String username;
	private String message;
	private boolean authorized;
}
