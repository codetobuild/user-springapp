package com.nagarro.userapp.repository;

import com.nagarro.userapp.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users , Integer> {
    @Query("SELECT u FROM Users u ORDER BY u.id LIMIT :limit OFFSET :offset")
    List<Users> findUsersWithLimitAndOffset(
            @Param("offset") int offset,
            @Param("limit") int limit
    );
}
