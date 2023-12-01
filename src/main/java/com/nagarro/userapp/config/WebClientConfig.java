package com.nagarro.userapp.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("randomUserWebClient")
    public WebClient randomUserWebClient() {
        return createWebClient("https://randomuser.me", 2000, 2);
    }

    @Bean
    @Qualifier("nationalityWebClient")
    public WebClient nationalityWebClient() {
        return createWebClient("https://api.nationalize.io", 1000, 1);
    }

    @Bean
    @Qualifier("genderizeWebClient")
    public WebClient genderizeWebClient() {
        return createWebClient("https://api.genderize.io", 1000, 1);
    }

    private WebClient createWebClient(String baseUrl, int connectTimeoutMillis, int timeoutSeconds) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMillis)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(timeoutSeconds))
                        .addHandlerLast(new WriteTimeoutHandler(timeoutSeconds)));

        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
