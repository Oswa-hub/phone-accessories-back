package phone_accessories.app.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import phone_accessories.app.entity.AdminUser;
import phone_accessories.app.repository.AdminUserRepository;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            adminUserRepository.deleteAll();

            if (adminUserRepository.findByUsername("admin").isEmpty()) {
                AdminUser admin = new AdminUser();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");

                adminUserRepository.save(admin);
                System.out.println("✅ Default admin account ('admin' / 'admin123') initialized successfully!");
            } else {
                System.out.println("ℹ️ Admin account already exists. Skipping initialization.");
            }
        };
    }
}