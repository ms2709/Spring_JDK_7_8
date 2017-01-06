package com.sockdemo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.io.Serializable;
import java.security.Principal;
import java.util.Map;

/**
 * Created by Administrator on 2017-01-06.
 */
@Controller
public class StompController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /*
    #1. SockJS로 요청시 호출되는 MessageMapping Method
    #2. SockJS로 요청시 aaa.aa로 요청이 안됨
     */
    @MessageMapping("/questStomp")
    public String procStompSockJsMessage(String msg, Principal principal){
        return msg.toUpperCase() + " by " + principal.getName();
    }

    /*
    #1. WebSocket으로 요청시 호출되는 MessageMapping Method
     */
    @MessageMapping("/questStomp.ws.message")
    public String procStompWebSocketMessage(@Payload String msg, @Headers Map map, @Header String simpDestination, Message<?> msgEx, MessageHeaders msgHeaders, Principal principal){
        return msg.toUpperCase() + " by " + principal.getName();
    }

    @MessageMapping("/questStomp.ws.private.{username}")
    public void procStompWebSocketMessageTo(@Payload String msg, @DestinationVariable("username") String username, Principal principal){
        simpMessagingTemplate.convertAndSend("/user/" + username + "/exchange/amq.direct/questStomp.ws.message", msg);
        simpMessagingTemplate.convertAndSend("/user/" + username + "/exchange/amq.direct/questStomp.ws.message1", msg);
        simpMessagingTemplate.convertAndSend("/user/" + username + "/exchange/direct/message", msg);
    }
    /*
    #1. Client가 Controller Methood에 대한 요청을 처리하고 보내주기 위한 방법
    #2. 보통은 Client가 @MessageMapping을 통해 요청 후 return 시 기본적으로 '/topic' 브로커를 사용하여 broadcasting을 함.
    #3. 이 메소드는 클라이언트 요청 후 Spring MVC처럼 작동하는 방식임.
     */
    @SubscribeMapping("/questStomp.ws/{submissionId}")
    public MessageEx getSubcribeInfo(@DestinationVariable long submissionId){
        return new MessageEx("Key", "Value # " + submissionId);
    }

    private class MessageEx implements Serializable {
        public MessageEx() { }

        public MessageEx(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        private String key;

        private String value;

        private static final long serialVersionUID = -3430525797548136557L;
    }
}
