package com.sockdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
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
        registry.addEndpoint("/questStomp.ws")
                .setHandshakeHandler(new RandomUsernameHandshakehandler());

        registry.addEndpoint("/questStomp")
                .setHandshakeHandler(new RandomUsernameHandshakehandler()).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.setApplicationDestinationPrefixes("/app")
//                .enableStompBrokerRelay("/topic","/queue")
//                .setRelayPort(15672);
                .enableSimpleBroker("/topic", "/queue", "/exchange");
    }

    private class RandomUsernameHandshakehandler extends DefaultHandshakeHandler {
        private String[] ADJECTIVES = {"aggressive", "annoyed", "black", "bootiful", "crazy", "elegant"};
        private String[] NOUNS = {"agent", "american", "anaconda", "caiman", "crab", "flamingo", "gorilla"};

        @Override
        protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes){
            String username = this.getRandom(ADJECTIVES) + "-" + this.getRandom(NOUNS) + getRandomInt(NOUNS.length);

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
