package com.xchat.xchat.service;


import com.xchat.xchat.user.User;
import com.xchat.xchat.user.UserPrincipal;
import com.xchat.xchat.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

//
//UserDetailsService
//we need it to return userdetails which is authentication object so userdetailsservice is used
//It is responsible to acess user from database and confirm if user is available/registered
//It resturns userdetails / User/(UserPrincipal)

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;



    //checking  if users is there id db
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }

        //if user not found then it is not authenticated
        //returning userdetails which is authenticated object
        //userprincipal adds remaining fields such as fullname etc
        return new UserPrincipal(user);



    }
}
