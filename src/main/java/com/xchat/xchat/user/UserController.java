package com.xchat.xchat.user;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private UserRepositoryImpl userRepositoryImpl;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    //message mapping to app/user.addUser
//    @MessageMapping("/user.addUser")
//    @SendTo("/user/public")
//    public User addUser(@Payload User user ) {
//        userService.saveUser(user);
//        return user;
//    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public User disconnectUser(@Payload User user) {
        userService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers() {
        return ResponseEntity.ok(userService.findConnectedUsers());
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> findByUsernameContaining(@RequestParam String name) {
        return ResponseEntity.ok(userService.findConnectedUsersByName(name));
    }

    @GetMapping("/getUserByEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping("/register")
    @ResponseBody
    public User  register(@RequestBody User user) {
         userService.saveUser(user);
         return user;
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody User user , HttpServletResponse response) {

        String jwt = userService.verify(user);

        Cookie cookie = new Cookie("token_vchat", jwt);

        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Use true in production to ensure it's only sent over HTTPS
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        cookie.setDomain("vchat.projects.veekshith.dev");
        response.addCookie(cookie);
        messagingTemplate.convertAndSend("/user/public/userjoined", user);


        log.info("user logged {}" ,user.getUsername() );

        return jwt;
    }

    @GetMapping
    public boolean canSendNotification(String username){
        return  userRepositoryImpl.canSendEmailNotificationForAwayUser(username);
    }

}
