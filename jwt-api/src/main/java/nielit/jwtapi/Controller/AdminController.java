package nielit.jwtapi.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AdminController {

    @GetMapping("/admin")
    public ResponseEntity<String> getAdminResource() {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().contains("ROLE_ADMIN")) {
            return ResponseEntity.ok("Access to admin resource granted.");
        }
        return ResponseEntity.ok("Access to admin resource denied.");
    }
}
