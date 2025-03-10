package com.xchat.xchat.chatRoom;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findFirstBySenderIdAndRecipientId(String senderId, String recipientId);


}
