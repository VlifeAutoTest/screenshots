package com.vlife.springmvc.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import com.vlife.springmvc.log.LoggerMessage;
import com.vlife.springmvc.log.LoggerQueue;


@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/log").setAllowedOrigins("*").withSockJS();
    }

//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic");
//        config.setApplicationDestinationPrefixes("/websocket");
//    }
//
//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//        registration.setMessageSizeLimit(128 * 1024);
//    }
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostConstruct
    public void pushLogger(){
        ExecutorService executorService= Executors.newFixedThreadPool(1);
//        Runnable processLog=new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                    	System.out.println("test config11111");
//                        LoggerMessage log = LoggerQueue.getInstance().poll();
//                        if(log!=null){
//                            if(messagingTemplate!=null)
//                                messagingTemplate.convertAndSend("/topic/pullLogger",log);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
        Runnable fileLog=new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String log = LoggerQueue.getInstance().pollFileLog();
                        if(log!=null){
                            if(messagingTemplate!=null)
                                messagingTemplate.convertAndSend("/topic/pullFileLogger",log);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        executorService.submit(fileLog);
//        executorService.submit(fileLog);
//        executorService.submit(processLog);
//        executorService.submit(processLog);
    }
}
