package com.xchat.xchat.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Service
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean canSendEmailNotificationForAwayUser(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(name));
        query.addCriteria(Criteria.where("Status").ne("ONLINE"));
        return mongoTemplate.exists(query, User.class);

    }
}
