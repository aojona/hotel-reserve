package ru.kirill.hotelreserve.config;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static ru.kirill.hotelreserve.enums.UserRole.ADMIN;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "aojonaAuth",
        scheme = "basic"
)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(config -> config
                        .requestMatchers("/api/**").hasAuthority(ADMIN.getAuthority())
                        .anyRequest().permitAll()
                )
                .httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
