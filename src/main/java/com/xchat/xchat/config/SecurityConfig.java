package com.xchat.xchat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private AuthenticationSuccessHandler customOAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http , ClientRegistrationRepository clientRegistrationRepository) throws Exception {
        //disabling csrf
        //CSRF (Cross-Site Request Forgery)  by default all request except get req csrf token should be sent

        return http.csrf(customizer -> customizer.disable())
                //removing auth from login and register endpoint
                .authorizeHttpRequests(request -> request

                        .requestMatchers( "/swagger-ui/**", "/v3/**", "/swagger-ui.html" ,"/login", "/register" ,  "/app/**" ,"/user/**" ,"ws/**" ,"/js/**","/hello" ,"/email",  "/static/**" ).permitAll()
                        .anyRequest().authenticated())
//                .oauth2Login(Customizer -> Customizer.defaultSuccessUrl("http://localhost:3000/" , true))




                // just basic auth with username and password
//                httpBasic(Customizer.withDefaults()).
                // Spring Security will not create an HTTP session for storing authentication detail each time  Instead, each request must contain authentication informationew session id is generated or jwt should be used
                //        In a stateless application, the server does not store any session information for the user. Each request from the client is independent and must carry all the necessary information, typically in the form of a token (e.g., JWT).
                //        This approach eliminates the need to maintain session data on the server, which can reduce memory usage and improve scalability.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2// Custom login page
                        .successHandler(customOAuth2SuccessHandler)  // Use the custom success handler
                        .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                                .authorizationRequestResolver(customAuthorizationRequestResolver(clientRegistrationRepository)))

                )
                .build();
//


    }


    //userdetails service used to verify username and password

//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("kiran")
//                .password("k@123")
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("harsh")
//                .password("h@123")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2);
//    }


    //How the Authentication Object is Used:
    //During Authentication:
    //
    //When a user tries to log in, Spring Security creates an Authentication object containing the username and password (credentials).
    //This object is passed to an AuthenticationManager, which attempts to authenticate the user.
    //After Authentication:
    //
    //If authentication is successful, the AuthenticationManager returns a fully populated Authentication object with the principal, authorities, and a flag indicating that the user is authenticated.
    //This Authentication object is then stored in the SecurityContext, which is accessible throughout the user's session.



    //username and password goes to authentication provider  it verfies and makes it authenticated object
    //    This is used for login . login will not work if removed
    @Bean
    public AuthenticationProvider authenticationProvider() {
//        authentication provider creates authenticated object in spring boot

        //there are multiple auth provides one of them is dauautyprovider  ,this is for database.  which is db auth providers
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        //this provider needs to connect to db and authenticate or check if user exista

        //using bcrypt encode password and then verify it
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));

        //using userdetailservice to verify username and pass ,
        // it fetches from user repo (postgress model) and verifies it user exists in db
        //

        provider.setUserDetailsService(userDetailsService);


        return provider;
    }


    //intitially req goes to auth manager then it delegates to auth provider  auth provdier looks for user from userdetail service
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }


    //for custom auth. ask everytime which account to choose

    @Bean
    public OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository) {
        return new CustomAuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/authorization");
    }


}
