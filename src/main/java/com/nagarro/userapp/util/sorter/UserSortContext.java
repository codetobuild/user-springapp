package com.nagarro.userapp.util.sorter;

import com.nagarro.userapp.entities.Users;

import java.util.List;

public class UserSortContext {
    private UserSortStrategy userSortStrategy;

    public void setSortStrategy(UserSortStrategy userSortStrategy) {
        this.userSortStrategy = userSortStrategy;
    }

    public List<Users> executeSort(List<Users> userList, String sortOrder) {

        return userSortStrategy.sort(userList, sortOrder);
    }
}
