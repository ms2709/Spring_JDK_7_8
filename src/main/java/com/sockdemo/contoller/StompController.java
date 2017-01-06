package com.sockdemo.contoller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Created by Administrator on 2017-01-06.
 */
@Controller
public class StompController {
    @MessageMapping("/questStomp")
    public String procStompSockJsMessage(String msg, Principal principal){
        return msg.toUpperCase() + " by " + principal.getName();
    }

    @MessageMapping("/questStomp.ws")
    public String procStompWebSocketMessage(String msg, Principal principal){
        return msg.toUpperCase() + " by " + principal.getName();
    }
}
