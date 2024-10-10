package com.xchat.xchat.Controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class MainController {


    @RequestMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }


    @GetMapping("/user-info")
    public Map<String , Object> userinfo(@AuthenticationPrincipal OAuth2User principal){
        return  principal.getAttributes();

    }

    @GetMapping("/user/me")
    public Principal user(Principal principal) {
        return principal;
    }
}
