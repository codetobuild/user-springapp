package com.nagarro.userapp.service;

import com.nagarro.userapp.entities.Users;
import com.nagarro.userapp.model.Gender;
import com.nagarro.userapp.model.Nationality;
import com.nagarro.userapp.repository.UserRepository;
import com.nagarro.userapp.util.GenderMapper;
import com.nagarro.userapp.util.NationalityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.nagarro.userapp.model.User;
import com.nagarro.userapp.util.UserMapper;
import org.slf4j.Logger;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;


@Slf4j
@Service
public class UserService {

    @Autowired
    @Qualifier("randomUserWebClient")
    WebClient randomUserWebClient;

    @Autowired
    @Qualifier("nationalityWebClient")
    WebClient nationalityWebClient;

    @Autowired
    @Qualifier("genderizeWebClient")
    WebClient genderizeWebClient;

    @Autowired
    UserRepository userRepository;

    public List<Users> createUser(Integer size) {

        List<User> userList = randomUserWebClient.get()
                .uri("/api?results=" + size)
                .retrieve()
                .bodyToMono(String.class)
                .map(UserMapper::mapToUser)
                .block();

         for(User user : userList){

             String userNationality = user.getNationality();
             String userGender = user.getGender();
             String name = user.getFirstname();

             System.out.println(name);

             // verify nationality
             List<Nationality> nationalityList = nationalityWebClient.get()
                     .uri("?name=" + name)
                     .retrieve()
                     .bodyToMono(String.class)
                     .map(NationalityMapper::mapToNationality)
                     .block();
             boolean isNationalityValid = nationalityList!=null && nationalityList.stream().anyMatch(s -> s.getNational().equalsIgnoreCase(userNationality));

             // verify gender
             Gender gender= genderizeWebClient.get()
                     .uri("?name=" + name)
                     .retrieve()
                     .bodyToMono(String.class)
                     .map(GenderMapper::mapToGender)
                     .block();
             boolean isGenderValid = gender != null && gender.getGender()!=null && gender.getGender().equalsIgnoreCase(userGender);

             String verificationStatus = "TO_BE_VERIFIED";
             if(isNationalityValid && isGenderValid){
                 verificationStatus = "VERIFIED";
             }
              user.setVerification_status(verificationStatus);

         }


         List<Users> usersToBeSaved = UserMapper.mapToUsersList(userList);
         List<Users> savedUsers = userRepository.saveAll(usersToBeSaved);

         log.info("Users Saved successfully to DB total users = {}", savedUsers.size());

         return savedUsers;
    }

}
