package com.xchat.xchat.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {


    @RequestMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }
}
