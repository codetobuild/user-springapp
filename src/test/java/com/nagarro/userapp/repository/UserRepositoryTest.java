package com.nagarro.userapp.repository;

import com.nagarro.userapp.entities.Users;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.nagarro.userapp.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;

import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private JpaRepository<Users, Integer> userRepositoryMock;

    @InjectMocks
    private UserRepository userRepository;

    @Test
    public void testFindUsersWithLimitAndOffset() {
        // Create a mock list of Users
        List<Users> users = new ArrayList<>();
        users.add(Users.builder().id(1).name("John Doe").email("john@doe.com").dob("1994-12-28T12:16:12.072Z").age(30).gender("male").nationality("NZ").verification_status("VERIFIED").build());
        users.add(Users.builder().id(1).name("Lucy Gibson").email("lucy@example.com").dob("1994-12-28T12:16:12.072Z").age(30).gender("female").nationality("NZ").verification_status("VERIFIED").build());


// Set up the mock behavior
        Pageable pageableMock = mock(Pageable.class);
        when(userRepositoryMock.findAll(pageableMock)).thenReturn((Page<Users>) users);

        // Call the findUsersWithLimitAndOffset method
        List<Users> actualUsers = userRepository.findUsersWithLimitAndOffset(0, 2);

        // Verify the results
        assertEquals(users, actualUsers);

        // Verify that the method was called with the correct parameters
//        verify(userRepositoryMock).findAll(
//                any(org.springframework.data.domain.Sort.class),
//                any(org.springframework.data.domain.Pageable.class)
//        );


        // Verify the results
        assertEquals(users, actualUsers);
    }
}