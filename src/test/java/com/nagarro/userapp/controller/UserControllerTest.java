package com.nagarro.userapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nagarro.userapp.dto.UserCreationDTO;
import com.nagarro.userapp.dto.UsersResponseDTO;
import com.nagarro.userapp.entities.Users;
import com.nagarro.userapp.model.PageInfo;
import com.nagarro.userapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getUsersTest() throws Exception {
        Users user1 = Users.builder().id(1).name("John Doe").email("john@doe.com").dob("1994-12-28T12:16:12.072Z").age(30).gender("male").nationality("NZ").verification_status("VERIFIED").build();

        Users user2 = Users.builder().id(2).name("Lucy Gibson").email("lucy@example.com").dob("1994-12-28T12:16:12.072Z").age(30).gender("female").nationality("US").verification_status("NOT_VERIFIED").build();

        List<Users> expectedUsersList = Arrays.asList(user1, user2);

        PageInfo pageInfo = PageInfo.builder().hasNextPage(true).hasPreviousPage(false).total(expectedUsersList.size()).build();

        UsersResponseDTO responseDTO = UsersResponseDTO.builder().data(expectedUsersList).pageInfo(pageInfo).build();

        when(userService.getSortedUsersWithOffsetAndLimit(0, 2, "Age", "even")).thenReturn(responseDTO);

        mockMvc.perform(get("/api/users")
                        .param("sortType", "Age")
                        .param("sortOrder", "even")
                        .param("limit", "2")
                        .param("offset", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(lessThanOrEqualTo(2))))
                .andExpect(jsonPath("$.data.[0].id").value(1))
                .andExpect(jsonPath("$.data.[1].id").value(2))
                .andExpect(jsonPath("$.data.[0].nationality").value("NZ"))
                .andExpect(jsonPath("$.data.[1].nationality").value("US"))
                .andExpect(jsonPath("$.data.[1].name").value("Lucy Gibson"))
                .andExpect(jsonPath("$.pageInfo").exists());
    }


    @Test
    public void shouldThrowBadRequestException_whenInvalidQueryParameter() throws Exception {
        mockMvc.perform(get("/api/users")
                        .param("sortType", "Age")
                        .param("sortOrder", "even")
                        .param("limit", "16")
                        .param("offset", "invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(status().is4xxClientError());

    }


    @Test
    void createUsersTest() throws Exception {
        int size = 1;
        UserCreationDTO userCreationDTO = UserCreationDTO.builder().size(size).build();
        ObjectMapper objectMapper = new ObjectMapper();

        List<Users> savedUsers = Collections.singletonList(
                Users.builder().id(1).name("John Doe").email("John.doe@example.com").dob("1987-04-24T18:25:58.908Z").age(36).gender("female").nationality("IN").verification_status("VERIFIED").date_created(LocalDateTime.now()).date_modified(LocalDateTime.now()).build());

        // Mock the userService behavior
        when(userService.createUser(1)).thenReturn(savedUsers);

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreationDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(size)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].nationality").value("IN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].verification_status").value("VERIFIED"));
    }


}