package nielit.jwtapi.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import nielit.jwtapi.Entity.UserEntity;
import nielit.jwtapi.Repository.UserRepository;

@Component
public class DatabaseInitializer implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		//check if database already populated
		if (userRepository.count() == 0) {
			//populate database
			UserEntity user1 = new UserEntity("admin", "$2a$12$GvvW0YLvpRGGRUupi/YMhe.ueIwvMmhV/6jWXbemwNqOvOpIJ6LVS", "ROLE_ADMIN");
			UserEntity user2 = new UserEntity("user", "$2a$12$w.pPHNnpSeP7KvStJ7f0weJdPReIfUDjMNitiOeXndgpsd6eNSA3K", "ROLE_USER");
			userRepository.save(user1);
			userRepository.save(user2);
		}
	}
	
}
