package htw.vs.websocket;

import htw.vs.base.CONFIG;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import javax.inject.Inject;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/backend").setAllowedOrigins("http://localhost:8080").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableStompBrokerRelay("/topic").setRelayHost(CONFIG.BROKER_HOST).setRelayPort(CONFIG.BROKER_PORT).setClientLogin(CONFIG.BROKER_LOGIN)
                .setClientPasscode(CONFIG.BROKER_PASSCODE);
    }


    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .nullDestMatcher().authenticated()
                //todo maverick fixen
                //.simpDestMatchers("/backend/**").hasRole("USER")
                //.simpDestMatchers("/backend/coordinator/**").hasRole("COORDINATOR")
                //.simpDestMatchers("/backend/admin/**").hasRole("ADMIN")
                .anyMessage().hasRole("COORDINATOR");

    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
