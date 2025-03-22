package com.castleedev.cabanassyc_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.List;

@Configuration
public class SecurityConfig {

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
         .cors().and()
         .csrf().disable()
         .authorizeHttpRequests(auth -> auth
            .requestMatchers("/contacts/**","/cabintypes/**", "/equipments/**", "/cabins/**", "/cabinequipments/**", "/rols/**", "/users/**", "/userrols/**", 
            "/workingdays/**", "/materialtypes/**", "/materials/**", "/materialrequests/**", "/tours/**", "/bookings/**", "/bookingtours/**", "/cabinbookings/**").permitAll()
         )
         .logout(logout -> logout.permitAll());

      return http.build();
   }

   @Bean
   public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true);
      config.setAllowedOrigins(List.of("http://localhost:3000"));
      config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
      config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
      source.registerCorsConfiguration("/**", config);
      return new CorsFilter(source);
   }
}
