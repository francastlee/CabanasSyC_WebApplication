package com.castleedev.cabanassyc_backend;

import org.apache.catalina.filters.RateLimitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.castleedev.cabanassyc_backend.Security.CustomUserDetailsService;
import com.castleedev.cabanassyc_backend.Security.JwtAuthEntryPoint;
import com.castleedev.cabanassyc_backend.Security.JwtAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   private JwtAuthEntryPoint jwtAuthEntryPoint;
   private CustomUserDetailsService userDetailsService;

   public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthEntryPoint jwtAuthEntryPoint) {
      this.jwtAuthEntryPoint = jwtAuthEntryPoint;
      this.userDetailsService = userDetailsService;
   }
   
   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(jwtAuthEntryPoint)
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
            .anyRequest().authenticated()
            )
            .headers(headers -> headers
               .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
               .contentSecurityPolicy(csp -> csp
                  .policyDirectives("default-src 'self'; script-src 'self'; style-src 'self'; img-src 'self' data:; font-src 'self'")
               )
               .httpStrictTransportSecurity(hsts -> hsts
                  .includeSubDomains(true)
                  .maxAgeInSeconds(31536000)
               )
               .xssProtection(xss -> xss
                  .headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
               )
               .contentTypeOptions(HeadersConfigurer.ContentTypeOptionsConfig::disable)
            )
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

      return http.build();
   }

   @Bean
   public FilterRegistrationBean<RateLimitFilter> rateLimitFilter() {
      FilterRegistrationBean<RateLimitFilter> registrationBean = new FilterRegistrationBean<>();
      registrationBean.setFilter(new RateLimitFilter());
      registrationBean.addUrlPatterns("/auth/login");
      return registrationBean;
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public JwtAuthenticationFilter jwtAuthenticationFilter() {
      return new JwtAuthenticationFilter();
   }

   @Bean
   public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true);
      config.setAllowedOriginPatterns(List.of("http://localhost:5173"));
      config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
      config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
      config.setExposedHeaders(List.of("Content-Disposition"));
      source.registerCorsConfiguration("/**", config);
      return new CorsFilter(source);
   }
}