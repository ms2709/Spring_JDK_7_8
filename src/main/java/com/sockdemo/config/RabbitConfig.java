package com.sockdemo.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

///**
// * Created by Administrator on 2017-01-24.
// */
@Configuration
public class RabbitConfig {
//    @Bean
//    public CachingConnectionFactory connectionFactory(){
//        return new CachingConnectionFactory("localhost");
//    }
//
//    @Bean
//    public AmqpAdmin amqpAdmin() {
//        return new RabbitAdmin(connectionFactory());
//    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1",5672);// 5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitMessagingTemplate rabbitMessagingTemplate() {
        return new RabbitMessagingTemplate(rabbitTemplate());
    }
//    @Bean
//    public Queue myQueue() {
//        return new Queue("myqueue");
//    }
}
