package project.order_rae.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Activa la configuración personalizada de seguridad web
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**", "/img/**").permitAll() // Público
                .requestMatchers("/usuarios/**").hasRole("ADMIN") // Solo ADMIN
                .requestMatchers("/perfil/**").authenticated() // Usuarios autenticados
                .anyRequest().authenticated() // Todo lo demás requiere login
            )
            .formLogin(form -> form
                .loginPage("/login") // Página de login personalizada
                .defaultSuccessUrl("/home", true) // Redirige al home tras login exitoso
                .permitAll() // Todos pueden acceder al login
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout") // Redirige al login tras cerrar sesión
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
