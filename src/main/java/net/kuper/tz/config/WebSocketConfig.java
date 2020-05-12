package net.kuper.tz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /** 允许使用topic,并且所有主题地址前缀为"/websocket" */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/websocket");
    }

    /**
     * 添加一个/hoody-websocket端点，客户端就可以通过这个端点来进行连接；
     * withSockJS作用是添加SockJS支持,
     * setAllowedOrigins(String... var1) 指定可以跨域访问的地址
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/hoody-websocket").setAllowedOrigins("*").withSockJS();
    }

}
