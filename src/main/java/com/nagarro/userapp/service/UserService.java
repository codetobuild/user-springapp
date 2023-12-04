package com.nagarro.userapp.service;

import com.nagarro.userapp.dto.UsersResponseDTO;
import com.nagarro.userapp.entities.Users;
import com.nagarro.userapp.model.Gender;
import com.nagarro.userapp.model.Nationality;
import com.nagarro.userapp.model.PageInfo;
import com.nagarro.userapp.repository.UserRepository;
import com.nagarro.userapp.util.GenderUtil;
import com.nagarro.userapp.util.NationalityUtil;
import com.nagarro.userapp.util.SortingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nagarro.userapp.model.User;
import com.nagarro.userapp.util.UserMapper;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApiService apiService;

    public UsersResponseDTO getSortedUsersWithOffsetAndLimit(int offset, int limit, String sortType, String sortOrder) {
        List<Users> dbUsers = userRepository.findUsersWithLimitAndOffset(offset, limit);
        List<Users> sortedUsers = SortingUtil.sortUsersByStrategy(dbUsers, sortType, sortOrder);

        PageInfo pageInfo = this.getPageInfoWithLimitAndOffset(offset, limit);

        return UsersResponseDTO.builder().data(sortedUsers).pageInfo(pageInfo).build();
    }



    public List<Users> createUser(Integer size) throws InterruptedException, ExecutionException {

        List<User> userList = apiService.getRandomUsers(size);

        List<CompletableFuture<User>> userVerifications = userList.stream()
                .map(user -> {
                    try {
                        return verifyUser(user, apiService);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        CompletableFuture<Void> allVerifications = CompletableFuture.allOf(
                userVerifications.toArray(new CompletableFuture[0])
        );

        // Wait for all user verifications to complete
        allVerifications.join();

        List<User> verificationCheckedUsers = userVerifications.stream()
                .map(CompletableFuture::join) // Extract the User result from each CompletableFuture
                .toList();

        List<Users> usersToBeSaved = UserMapper.mapToUsersList(verificationCheckedUsers);
        List<Users> savedUsers = userRepository.saveAll(usersToBeSaved);

         return savedUsers;
     }

    private CompletableFuture<User> verifyUser(User user, ApiService apiService) throws InterruptedException {
        String userNationality = user.getNationality();
        String userGender = user.getGender();

        CompletableFuture<List<Nationality>> futureNationality = apiService.getNationalityWithUserName(userNationality);
        CompletableFuture<Gender> futureGender = apiService.getGenderByUserName(userGender);

        return CompletableFuture.allOf(futureNationality, futureGender)
                .thenApply(ignored -> {
                    List<Nationality> nationalityList = futureNationality.join();
                    Gender gender = futureGender.join();

                    boolean isNationalityAndGenderVerified = GenderUtil.isGenderValid(userGender, gender)
                            && NationalityUtil.isUserNationalityValid(userNationality, nationalityList);

                    String verificationStatus = isNationalityAndGenderVerified ? "VERIFIED" : "TO_BE_VERIFIED";
                    user.setVerification_status(verificationStatus);

                    return user;
                });
    }

    public PageInfo getPageInfoWithLimitAndOffset(int offset, int limit) {
        long count = userRepository.count();
        int totalPages = (int) Math.ceil((double) count / limit);

        boolean hasNextPage = (offset + limit) < count;
        boolean hasPreviousPage = offset > 0;

        return PageInfo.builder().hasNextPage(hasNextPage).hasPreviousPage(hasPreviousPage).total(count).build();
    }

}

