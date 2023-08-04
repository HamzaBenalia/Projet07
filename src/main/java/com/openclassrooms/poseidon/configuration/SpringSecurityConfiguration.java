package com.openclassrooms.poseidon.configuration;
import com.openclassrooms.poseidon.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe de configuration de spring security.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * configuration du service du recherche des utilisateurs et du hashage des mots de passes.
     *
     * @param auth {@link AuthenticationManagerBuilder}
     * @throws Exception
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Defining the passwordEncoder here, to avoid plain-text manipulations
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * configuration des accès aux routes sécurisées et autorisées.
     *
     * @param http {@link HttpSecurity}
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login", "/index", "/app-logout").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/user/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/")
                );
        return http.build();
    }
}
