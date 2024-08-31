registerd message borker  - /user

setApplicationDestinationPrefixes -/app  //whenever we send to messagemapping /app prefix is added
setUserDestinationPrefix           -/user  //whenever user subscribes to broker /user prefix is added


    sender          --      queue               --reciever
  alic send to John ---   user/john/queue/messages  --- John subscribes


 to get notification subscribe to   /user/public



user sends message to app/chat
  



