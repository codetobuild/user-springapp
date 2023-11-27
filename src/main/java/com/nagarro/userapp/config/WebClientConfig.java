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
    public WebClient randomUserWebClient(){
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(2))
                        .addHandlerLast(new WriteTimeoutHandler(2)));

        WebClient webClient = WebClient.builder().baseUrl("https://randomuser.me")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return webClient;
    }

    @Bean
    @Qualifier("nationalityWebClient")
    public WebClient nationalityWebClient(){
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(1))
                        .addHandlerLast(new WriteTimeoutHandler(1)));

        WebClient webClient = WebClient.builder().baseUrl("https://api.nationalize.io")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return webClient;
    }

    @Bean
    @Qualifier("genderizeWebClient")
    public WebClient genderizeWebClient(){
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(1))
                        .addHandlerLast(new WriteTimeoutHandler(1)));

        WebClient webClient = WebClient.builder().baseUrl("https://api.genderize.io")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return webClient;
    }

}
