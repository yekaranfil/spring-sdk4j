package dileksoft.sdk.config;

import dileksoft.sdk.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordUpdateScript {

    @Bean
    public CommandLineRunner updatePasswords(UserRepository userRepository) {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userRepository.findAll().forEach(user -> {
                // Eğer şifre zaten BCrypt ile hash'lenmemişse
                if (!user.getPasswordHash().startsWith("$2a$")) {
                    user.setPasswordHash(encoder.encode(user.getPasswordHash()));
                    userRepository.save(user);
                }
            });
        };
    }
}
