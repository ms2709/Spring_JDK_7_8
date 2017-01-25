package com.sockdemo.config;

import com.munseop.common.model.CurrentMember;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
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
        registry
                .addEndpoint("/chat.ws")
                .setHandshakeHandler(new UsernameHandshakehandler());

        registry
                .addEndpoint("/chat")
                .setHandshakeHandler(new UsernameHandshakehandler()).withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration){
        registration
                .setMessageSizeLimit(1024 * 1024 * 20) //20M
                .setSendBufferSizeLimit(1024 * 1024) //1M
                .setSendTimeLimit(60 * 1000); // 60sec
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry
                .setApplicationDestinationPrefixes("/app")
//                .enableStompBrokerRelay("/topic","/queue", "/exchange") //broker relay (message broker 서비스 사용시 : ex. rabbitmq)
//                .setSystemPasscode("guest")
//                .setSystemPasscode("guest")
//                .setRelayPort(61613)
//                .setVirtualHost("/")
//                .setSystemHeartbeatSendInterval(100)
//                .setSystemHeartbeatReceiveInterval(100)
//                .setAutoStartup(true);

              .enableSimpleBroker("/topic", "/queue", "/exchange"); // simple broker 사용시 (메모리상에서 사용하는 broker)
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
