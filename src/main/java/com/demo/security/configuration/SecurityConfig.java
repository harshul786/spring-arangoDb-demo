package com.demo.security.configuration;

import com.demo.security.repository.UserRoleRepository;
import com.demo.security.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthFilter jwtAuthFilter;
    @Autowired
    UserRoleRepository userRoleRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/api/v1/auth/signIn").permitAll()
                                .requestMatchers("/api/v1/auth/signUp").permitAll()
//                                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/api/v1/admin/listUsers").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/v1/admin/addPermissionsToRole/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/api/v1/admin/**").hasAuthority("ADMIN_READ")
                                .requestMatchers(HttpMethod.POST,"/api/v1/admin/**").hasAuthority("ADMIN_CREATE")
                                .requestMatchers(HttpMethod.PUT,"/api/v1/admin/**").hasAuthority("ADMIN_UPDATE")
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/admin/**").hasAuthority("ADMIN_DELETE")
//                                .requestMatchers("/api/v1/character/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET,"/api/v1/character/**").hasAnyAuthority("ADMIN_READ", "USER_READ")
                                .requestMatchers(HttpMethod.POST,"/api/v1/character/**").hasAnyAuthority("ADMIN_CREATE",  "USER_CREATE")
                                .requestMatchers(HttpMethod.PUT,"/api/v1/character/**").hasAnyAuthority("ADMIN_UPDATE",  "USER_UPDATE")
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/character/**").hasAnyAuthority("ADMIN_DELETE", "USER_DELETE")
                                .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(
                        jwtAuthFilter, UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
