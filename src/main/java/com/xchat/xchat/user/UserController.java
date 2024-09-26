package com.xchat.xchat.user;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
@RequiredArgsConstructor
public class UserController {



    private final UserService userService;


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

        Cookie cookie = new Cookie("token", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Use true in production to ensure it's only sent over HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days


        response.addCookie(cookie);

        messagingTemplate.convertAndSend("/user/public/userjoined", user);

        return jwt;
    }
}
