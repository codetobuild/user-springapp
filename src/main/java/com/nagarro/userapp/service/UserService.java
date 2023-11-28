package com.nagarro.userapp.service;

import com.nagarro.userapp.dto.UsersResponseDTO;
import com.nagarro.userapp.entities.Users;
import com.nagarro.userapp.model.Gender;
import com.nagarro.userapp.model.Nationality;
import com.nagarro.userapp.model.PageInfo;
import com.nagarro.userapp.repository.UserRepository;
import com.nagarro.userapp.util.GenderMapper;
import com.nagarro.userapp.util.NationalityMapper;
import com.nagarro.userapp.util.SortingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nagarro.userapp.model.User;
import com.nagarro.userapp.util.UserMapper;
import org.slf4j.Logger;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


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

    public UsersResponseDTO getSortedUsersWithOffsetAndLimit(int offset, int limit, String sortType, String sortOrder) {
        List<Users> dbUsers = userRepository.findUsersWithLimitAndOffset(offset, limit);
        PageInfo pageInfo = this.getPageInfoWithLimitAndOffset(offset, limit);
        return UsersResponseDTO.builder().data(dbUsers).pageInfo(pageInfo).build();
     }


    public PageInfo getPageInfoWithLimitAndOffset(int offset, int limit){
        long count = userRepository.count();
        int totalPages = (int) Math.ceil((double) count / limit);

        boolean hasNextPage = (offset + limit) < count;
        boolean hasPreviousPage = offset > 0;

        return PageInfo.builder().hasNextPage(hasNextPage).hasPreviousPage(hasPreviousPage).total(totalPages).build();
    }


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
