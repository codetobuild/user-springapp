package com.nagarro.userapp.util.sorter;

import com.nagarro.userapp.entities.Users;
import com.nagarro.userapp.model.User;

import java.util.List;

public interface UserSortStrategy {
    List<Users> sort(List<Users> userList, String sortOrder);
}
