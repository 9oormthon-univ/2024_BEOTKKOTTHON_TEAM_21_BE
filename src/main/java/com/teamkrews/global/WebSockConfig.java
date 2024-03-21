package com.teamkrews.global;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트에서 보낸 메세지를 받을 prefix
        config.setApplicationDestinationPrefixes("/send");

        // 구독하는 클라이언트들에게 메시지 전송
        config.enableSimpleBroker("/chatRoom");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // stomp 웹소켓 endpoint 설정
        // ws://localhost:8080/ws
        // http://localhost
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}