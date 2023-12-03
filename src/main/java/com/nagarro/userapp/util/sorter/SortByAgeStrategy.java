package com.nagarro.userapp.util.sorter;

import com.nagarro.userapp.entities.Users;
import com.nagarro.userapp.model.User;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SortByAgeStrategy implements UserSortStrategy{
    @Override
    public List<Users> sort(List<Users> userList, String sortOrder) {
        Predicate<Users> sortingPredicate = user -> sortOrder.equalsIgnoreCase("even") == (user.getAge() % 2 != 0);

        return userList.stream()
                .collect(Collectors.partitioningBy(sortingPredicate))
                .values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
