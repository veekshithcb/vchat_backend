package com.xchat.xchat.user;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {


    //In Spring Boot, specifically within the context of Spring Security, the UserDetails class refers to an interface that is used to represent the core user information necessary for authentication and authorization. It is a part of the Spring Security framework and plays a crucial role in managing and securing user credentials.

    //The UserDetails interface is central to Spring Security’s authentication mechanism.
    // It provides a standard way to represent user information that can be used to authenticate and authorize users within your application.

    //user detail object respresentation of actual user
    //userprincipal is custom impl of userdetails
    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }


    //just passing hardcoded  role as user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }
    //getting userdetails from db and creating UserDetails object
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
