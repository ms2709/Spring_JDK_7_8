package com.sockdemo.contoller;

import com.munseop.project.model.ChatMessage;
import com.munseop.project.model.Room;
import com.munseop.project.model.RoomDetail;
import com.munseop.project.service.ChatMessageService;
import com.munseop.project.service.RoomService;
import com.sockdemo.config.WebSocketConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017-01-11.
 */
@Controller
public class ChatController {

    private final Logger logger = LoggerFactory.getLogger( ChatController.class );

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat.ws.message.{roomId}")
    public void messageToUsersInRoom(@DestinationVariable("roomId")String roomId,
                                     @Headers Map map,
                                     @Payload ChatMessage msg,
                                     MessageHeaders msgHeaders,
                                     Principal principal){

        chatMessageService.saveMessage(msg);
        simpMessagingTemplate.convertAndSend("/topic/chat.ws.message." + roomId, msg);
    }

    @MessageMapping("/chat.ws.private.{recvuser}")
    public void messageToUser(@DestinationVariable("recvuser")String recvuser,
                                     @Headers Map map,
                                     @Payload ChatMessage msg,
                                     MessageHeaders msgHeaders,
                                     Principal principal){

        chatMessageService.saveMessage(msg);

        String destination = "/user/" + recvuser + "/exchange/amq.direct/chat.ws.message." + msg.getRoomId();

        logger.info(destination);
        simpMessagingTemplate.convertAndSend(destination, msg);
    }
}
