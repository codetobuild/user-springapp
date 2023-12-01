package com.nagarro.userapp.controller;

import com.nagarro.userapp.dto.UserCreationDTO;
import com.nagarro.userapp.dto.UsersResponseDTO;
import com.nagarro.userapp.entities.Users;
import com.nagarro.userapp.exceptions.ResourceNotFoundException;
import com.nagarro.userapp.exceptions.ValidationException;
import com.nagarro.userapp.service.UserService;
import com.nagarro.userapp.validators.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/test")
    public String testApi() {
        try {
            throw new ResourceNotFoundException("test resource not found");
        } catch (ResourceNotFoundException ex) {
            // Optionally log or handle the exception
            throw ex; // Rethrow the caught exception
        }catch (Exception ex){
            System.out.println("#######Error########");
        }
        return "api success";
    }

    @GetMapping
    public ResponseEntity<UsersResponseDTO> getUsers(
            @RequestParam String sortType,
            @RequestParam String sortOrder,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        ValidatorUtils.validateUserParams(sortType, sortOrder, limit, offset);
        UsersResponseDTO res = userService.getSortedUsersWithOffsetAndLimit(offset, limit, sortType, sortOrder);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping
    public ResponseEntity<List<Users>> createUsers(@RequestBody UserCreationDTO userRequestBody) throws ValidationException, ExecutionException, InterruptedException {
        ValidatorUtils.validateUserCreationDTO(userRequestBody);
        List<Users> savedUsers = userService.createUser(userRequestBody.getSize());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsers);
    }

}