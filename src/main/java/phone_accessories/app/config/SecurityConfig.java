package phone_accessories.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import phone_accessories.app.service.CustomUserDetailsService;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    // Constructor injection for your custom service
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configure(http)) // 👈 Ensure your CorsConfig bean is linked!
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/auth/login").permitAll() // 👈 Open both variations just in case
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {});

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        // Pass your custom service directly into the constructor here! 🎯
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);

        // Set the password encoder engine
        provider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(provider);
    }
}