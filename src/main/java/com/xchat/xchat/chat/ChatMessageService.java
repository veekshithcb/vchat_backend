package com.xchat.xchat.chat;

import com.xchat.xchat.chatRoom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow(); // You can create your own dedicated exception
        chatMessage.setChatId(chatId);
        repository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {

        var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        System.out.println(chatId);



        return chatId.map(repository::findByChatId).orElse(new ArrayList<>());


//        List<ChatMessage> res = new ArrayList<>() ;
//        List<ChatMessage> res1 =  repository.findBySenderIdAndRecipientId(senderId ,recipientId );
//        List<ChatMessage> res2 =  repository.findBySenderIdAndRecipientId( recipientId  , senderId);
//        res.addAll(res1);
//        res.addAll(res2);
//        return res;
    }
}
