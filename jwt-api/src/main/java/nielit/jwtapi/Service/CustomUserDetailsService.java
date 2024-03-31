package nielit.jwtapi.Service;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import nielit.jwtapi.Entity.UserEntity;
import nielit.jwtapi.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(user.getUsername(),
                user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getAuthority())));

        // Check against a hard-coded username for testing purposes

        //     if ("adminUser".equals(username)) {
        //         return User.builder()
        //                 .username("adminUser")
        //                 .password( passwordEncoder.encode("adminPassword")) //In production, fetch encoded password from database
        //                 .authorities("ROLE_ADMIN")
        //                 .build();
        //     } 
        //     return User.builder()
        //         .username("testUser")
        //         .password(passwordEncoder.encode("testPassword"))
        //         .authorities("ROLEUSER")
        //         .build();

        // }
    }

}

