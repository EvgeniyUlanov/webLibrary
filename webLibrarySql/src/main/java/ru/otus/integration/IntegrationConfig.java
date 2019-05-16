package ru.otus.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.dto.CommentDto;

@IntegrationComponentScan
@EnableIntegration
@Configuration
public class IntegrationConfig {

    @Bean(name = PollerMetadata.DEFAULT_POLLER )
    public PollerMetadata poller () {
        return Pollers.fixedRate(1000).get() ;
    }

    @Bean
    public QueueChannel commentInputChannel() {
        return MessageChannels.queue().datatype(CommentDto.class).get();
    }

    @Bean
    public PublishSubscribeChannel commentOutputChannel() {
        return MessageChannels.publishSubscribe().datatype(CommentDto.class).get();
    }

    @Bean
    public IntegrationFlow commentFlow() {
        return IntegrationFlows.from("commentInputChannel")
                .handle("commentCheckServiceImpl", "checkForbiddenWords")
                .channel("commentOutputChannel")
                .get();
    }
}
