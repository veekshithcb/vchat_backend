package com.xchat.xchat.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends MongoRepository<User, String> {
    List<User> findAllByStatus(Status status);

    List<User> findByUsername(String username);

    User findOneByUsername(String username);

    User findByEmail(String email);

    @Query("{ 'username': { $regex: '?0', $options: 'i' } }")
    List<User> findByUsernameContaining(String substring);


}
