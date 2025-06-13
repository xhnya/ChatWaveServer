package com.xhn.chat.chatwaveserver.base.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xhn
 * @date 2025/6/12 14:32
 * @description
 */
@Configuration
public class RabbitConfig {


    public static final String USER_REGISTER_EXCHANGE_NAME = "user.register.exchange";

    @Bean
    public FanoutExchange userRegisterExchange() {
        return new FanoutExchange(USER_REGISTER_EXCHANGE_NAME);
    }


    @Bean
    public Queue mailQueue() {
        return new Queue("user.register.mail.queue", true);
    }

    @Bean
    public Binding mailBinding(FanoutExchange userRegisterExchange, Queue mailQueue) {
        return BindingBuilder.bind(mailQueue).to(userRegisterExchange);
    }

    @Bean
    public Queue profileQueue() {
        return new Queue("user.register.profile.queue", true);
    }

    @Bean
    public Binding profileBinding(FanoutExchange userRegisterExchange, Queue profileQueue) {
        return BindingBuilder.bind(profileQueue).to(userRegisterExchange);
    }




//  好友请求
    @Bean
    public TopicExchange friendRequestExchange() {
        return new TopicExchange("friendRequestExchange");
    }

    @Bean
    public Queue friendRequestQueue() {
        return new Queue("friendRequestQueue");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(friendRequestQueue())
                .to(friendRequestExchange())
                .with("friend.request");
    }

}
