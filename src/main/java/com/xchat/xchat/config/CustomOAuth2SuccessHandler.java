package com.xchat.xchat.config;


import com.xchat.xchat.service.JWTService;
import com.xchat.xchat.user.*;
import com.xchat.xchat.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

import static com.xchat.xchat.user.Status.ONLINE;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        System.out.println("OAuth2 Authentication Successful!");

        if (authentication.getPrincipal() instanceof OidcUser) {
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            String email = oidcUser.getEmail();
            String username = oidcUser.getFullName();
            Optional<User> user = Optional.ofNullable(userService.getUserByEmail(email));
            if (user.isEmpty()) {
                user = Optional.ofNullable(userService.saveUser(new User(username, email, "", Status.ONLINE)));
            }
            String token = jwtService.generateToken(user.get().getUsername());
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);                       // Ensures the cookie is only sent over HTTPS
            cookie.setDomain(".veekshith.dev");
            // Set cookie attributes
            cookie.setMaxAge(7 * 24 * 60 * 60);  // 1 week

            cookie.setPath("/");
            response.addCookie(cookie);
//            response.sendRedirect("http://localhost:3000/");
            response.sendRedirect("https://vchat.projects.veekshith.dev/");

        }
    }
}