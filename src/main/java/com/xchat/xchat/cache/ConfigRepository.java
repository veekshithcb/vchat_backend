package com.xchat.xchat.cache;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConfigRepository extends MongoRepository<ConfigEntity, String> {
    List<ConfigEntity> findAll();

}
