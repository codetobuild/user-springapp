package com.nagarro.userapp.service;

import com.nagarro.userapp.entities.Users;
import com.nagarro.userapp.model.Gender;
import com.nagarro.userapp.model.Nationality;
import com.nagarro.userapp.model.User;
import com.nagarro.userapp.util.GenderUtil;
import com.nagarro.userapp.util.NationalityUtil;
import com.nagarro.userapp.util.NationalityUtil;
import com.nagarro.userapp.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ApiService {

    @Autowired
    @Qualifier("randomUserWebClient")
    WebClient randomUserWebClient;

    @Autowired
    @Qualifier("nationalityWebClient")
    WebClient nationalityWebClient;

    @Autowired
    @Qualifier("genderizeWebClient")
    WebClient genderizeWebClient;

    public List<User> getRandomUsers(Integer size){
        List<User> userList = randomUserWebClient.get()
                .uri("/api?results=" + size)
                .retrieve()
                .bodyToMono(String.class)
                .map(UserMapper::mapToUser)
                .block();

        return userList;
    }
    @Async("asyncExecutor")
    public CompletableFuture<List<Nationality>> getNationalityWithUserName(String name) throws InterruptedException {
        return nationalityWebClient.get()
                .uri("?name=" + name)
                .retrieve()
                .bodyToMono(String.class)
                .map(NationalityUtil::mapToNationality)
                .toFuture();
     }


    @Async("asyncExecutor")
    public CompletableFuture<Gender> getGenderByUserName(String name) {
        return  genderizeWebClient.get()
                .uri("?name=" + name)
                .retrieve()
                .bodyToMono(String.class)
                .map(GenderUtil::mapToGender)
                .toFuture();
    }


}
