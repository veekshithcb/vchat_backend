package com.xchat.xchat.user;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Stack;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String username;
    private String email;
    private String password;
    private Status status;


}
