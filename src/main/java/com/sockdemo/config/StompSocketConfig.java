package com.sockdemo.config;

import com.munseop.common.model.CurrentMember;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017-01-06.
 */
@Configuration
@EnableWebSocketMessageBroker
public class StompSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat.ws")
                .setHandshakeHandler(new UsernameHandshakehandler());

        registry.addEndpoint("/chat")
                .setHandshakeHandler(new UsernameHandshakehandler()).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.setApplicationDestinationPrefixes("/app")
//                .enableStompBrokerRelay("/topic","/queue")
                .enableSimpleBroker("/topic", "/queue", "/exchange");
    }

    private class UsernameHandshakehandler extends DefaultHandshakeHandler {

        private String[] ADJECTIVES = {"a", "b", "c", "d", "e", "f", "g", "i", "j", "k", "l", "m", "n"};
        private String[] NOUNS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

        @Override
        protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes){
            CurrentMember currentmember = (CurrentMember)((ServletServerHttpRequest) request)
                    .getServletRequest()
                    .getSession()
                    .getAttribute("currentMember");

            String username = "";
            if(currentmember != null){
                username = currentmember.getLoginId();
            }
            else{
                username = this.getRandom(ADJECTIVES) + "-" + this.getRandom(NOUNS) + getRandomInt(NOUNS.length);
            }

            return new UsernamePasswordAuthenticationToken(username, null);
        }

        private String getRandom(String[] array){
            int random = getRandomInt(array.length);
            return array[random];
        }

        private int getRandomInt(int n){
            Random rand = new Random();
            return rand.nextInt(n);
        }
    }
}
