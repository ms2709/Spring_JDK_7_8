package com.sockdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017-01-05.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry r) {
        r.addHandler(questionHandler(), "quest.ws");
        r.addHandler(questionHandler(), "quest").withSockJS();
    }

    @Bean
    public WebSocketHandler questionHandler(){
        return new QuestionsHandler();
    }
    class QuestionsHandler extends TextWebSocketHandler {
        private final Logger logger = LoggerFactory.getLogger( QuestionsHandler.class );
        private Set<WebSocketSession> sessionSet = new HashSet<WebSocketSession>();


        public QuestionsHandler() {
            super();

            this.logger.info("create SocketHandler instance!");
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

            super.afterConnectionClosed(session, status);
            sessionSet.remove(session);
            this.logger.info("remove session!");

        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            super.afterConnectionEstablished(session);
            sessionSet.add(session);
            this.logger.info("add session!");
        }

        @Override
        public void handleMessage(WebSocketSession session,
                                  WebSocketMessage<?> message) throws Exception {
            super.handleMessage(session, message);

            this.logger.info("receive message:" + message.toString());

            sendMessage("===> " + message);
        }

        @Override
        public void handleTransportError(WebSocketSession session,
                                         Throwable exception) throws Exception {
            this.logger.error("web socket error!", exception);
        }

//        @Override
//        public boolean supportsPartialMessages() {
//            this.logger.info("call method!");
//
//            return super.supportsPartialMessages();
//        }

        public void sendMessage(String message) {
            for (WebSocketSession session : this.sessionSet) {
                if (session.isOpen()) {
                    try {
                        session.sendMessage(new TextMessage(message));
                    } catch (Exception ignored) {
                        this.logger.error("fail to send message!", ignored);
                    }
                }
            }
        } // method end
    } // sub class end
}
