package com.nagarro.userapp.service;

import com.nagarro.userapp.model.Gender;
import com.nagarro.userapp.model.Nationality;
import com.nagarro.userapp.model.User;
import com.nagarro.userapp.repository.UserRepository;
import com.nagarro.userapp.util.GenderMapper;
import com.nagarro.userapp.util.NationalityMapper;
import com.nagarro.userapp.util.SortingUtil;
import com.nagarro.userapp.util.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nagarro.userapp.dto.UserCreationDTO;
import com.nagarro.userapp.dto.UsersResponseDTO;
import com.nagarro.userapp.entities.Users;
import com.nagarro.userapp.model.PageInfo;
import com.nagarro.userapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    @Qualifier("randomUserWebClient")
    private WebClient randomUserWebClient;

    @Mock
    @Qualifier("nationalityWebClient")
    private WebClient nationalityWebClient;

    @Mock
    @Qualifier("genderizeWebClient")
    private WebClient genderizeWebClient;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getSortedUsersWithOffsetAndLimit() {
        // Prepare input data
        int offset = 10;
        int limit = 20;
        String sortType = "name";
        String sortOrder = "asc";

        // Mock repository behavior
        List<Users> users = new ArrayList<>();
        users.add(new Users(1, "John Doe", "johndoe@example.com", "1234567890", "Male", "USA"));
        users.add(new Users(2, "Jane Doe", "janedoe@example.com", "9876543210", "Female", "India"));

        Pageable pageable = PageRequest.of(offset / limit, limit, SortingUtil.get.getSortDirection(sortType), sortType);
        when(userRepository.findAll(pageable)).thenReturn((Page<Users>) users);

        // Call the method under test
        UsersResponseDTO usersResponseDTO = userService.getSortedUsersWithOffsetAndLimit(offset, limit, sortType, sortOrder);

        // Verify results
        assertEquals(users, usersResponseDTO.getData());
        assertEquals(PageInfo.builder().hasNextPage(true).hasPreviousPage(true).total(2).build(), usersResponseDTO.getPageInfo());
    }

    @Test
    void getPageInfoWithLimitAndOffset() {
    }

    @Test
    void createUser() {
//        Integer size = 1;
//        User userFromWebClient = User.builder()
//                .firstname("Pat")
//                .lastname("Beck")
//                .name("Mr. Pat Beck")
//                .email("pat.beck@example.com")
//                .dob("1987-03-20T10:07:58.582Z")
//                .age(36)
//                .gender("male")
//                .nationality("US")
//                .verification_status("VERIFIED")
//                .build();
//
//        List<User> userList = Collections.singletonList(userFromWebClient);
//
//        // Mock WebClient responses
//        when(randomUserWebClient.get()
//                .uri("/api?results=" + size)
//                .retrieve()
//                .bodyToMono(String.class)
//                .map(UserMapper::mapToUser)
//                .block())
//                .thenReturn(userList);
//        when(nationalityWebClient.get()
//                .uri("?name=" + userFromWebClient.getFirstname())
//                .retrieve()
//                .bodyToMono(String.class)
//                .map(NationalityMapper::mapToNationality)
//                .block())
//                .thenReturn(Collections.singletonList(Nationality.builder().national("US").build()));
//
//        when(genderizeWebClient.get()
//                .uri("?name=" + userFromWebClient.getFirstname())
//                .retrieve()
//                .bodyToMono(String.class)
//                .map(GenderMapper::mapToGender)
//                .block())
//                .thenReturn(Gender.builder().gender("male").build());
//
//
//        List<Users> usersToBeSaved = UserMapper.mapToUsersList(userList);
//        // Mock UserRepository save
//        when(userRepository.saveAll(usersToBeSaved)).thenReturn(usersToBeSaved);
//
//        List<Users> savedUsers = userService.createUser(size);
//
//        assertEquals(size, savedUsers.size());
//        Users savedUser = savedUsers.get(0);
//        assertEquals(userFromWebClient.getFirstname(), savedUser.getName());
//        assertEquals(userFromWebClient.getNationality(), savedUser.getNationality());
//        assertEquals("VERIFIED", savedUser.getVerification_status());
//
//        // Verify interactions
//        verify(userRepository, times(1)).saveAll(anyList());

    }
}