package com.castleedev.cabanassyc_backend.Security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Services.Implementations.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final String frontendLoginUrl;
    private final JwtGenerator jwtGenerator;
    private final UserService userService;

    public GoogleOAuth2SuccessHandler(
            @Value("${app.frontend.login-url:http://localhost:5173/login}") String frontendLoginUrl,
            JwtGenerator jwtGenerator,
            UserService userService
    ) {
        this.frontendLoginUrl = frontendLoginUrl;
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            org.springframework.security.core.Authentication authentication) throws IOException, ServletException {
         OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = (OAuth2User) oauthToken.getPrincipal();
        Map<String, Object> attrs = oAuth2User.getAttributes();

        String email = (String) attrs.get("email");
        String name = (String) attrs.get("name");
        String picture = (String) attrs.get("picture");

        UserModel user = userService.upsertGoogleUser(email, name, picture);

        String token = jwtGenerator.generateToken(user.getEmail());

        String redirectUrl = frontendLoginUrl + "?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);
        response.sendRedirect(redirectUrl);
    }
}
