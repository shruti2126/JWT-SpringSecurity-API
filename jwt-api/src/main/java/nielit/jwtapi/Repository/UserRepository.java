package nielit.jwtapi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nielit.jwtapi.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    
}
