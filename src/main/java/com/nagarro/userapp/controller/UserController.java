package com.nagarro.userapp.controller;


import com.nagarro.userapp.dto.UserCreationDTO;
import com.nagarro.userapp.entities.Users;
import com.nagarro.userapp.model.User;
import com.nagarro.userapp.service.UserService;
import com.nagarro.userapp.util.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public String getUsers(
            @RequestParam String sortType,
            @RequestParam String sortOrder,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        logger.info("query sort type: {}", sortType);
        logger.info("sort order: {}", sortOrder);
        logger.info("limit: {}", limit);
        logger.info("offset: {}", offset);

        return "users from server";
    }

    @PostMapping
    public ResponseEntity<List<Users>> createUsers(@RequestBody UserCreationDTO userRequestBody) {

        List<Users> savedUsers = userService.createUser(userRequestBody.getSize());
        return ResponseEntity.status(HttpStatus.OK).body(savedUsers);
    }

}