package com.nagarro.userapp.util;

import com.nagarro.userapp.entities.Users;
import com.nagarro.userapp.util.sorter.SortByAgeStrategy;
import com.nagarro.userapp.util.sorter.SortByNameStrategy;
import com.nagarro.userapp.util.sorter.UserSortContext;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SortingUtil {

    public static List<Users> sortUsersByStrategy(List<Users> users, String sortType, String sortOrder) {
        sortType = sortType.toUpperCase();
        UserSortContext userSortContext = new UserSortContext();

        switch (sortType) {
            case "AGE":
                userSortContext.setSortStrategy(new SortByAgeStrategy());
                return userSortContext.executeSort(users, sortOrder);
            case "NAME":
                userSortContext.setSortStrategy(new SortByNameStrategy());
                return userSortContext.executeSort(users, sortOrder);
            default:
                throw new IllegalArgumentException("Unsupported sortType: " + sortType);
        }
    }
}
