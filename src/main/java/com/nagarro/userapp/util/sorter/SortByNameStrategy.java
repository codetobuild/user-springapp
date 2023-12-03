package com.nagarro.userapp.util.sorter;

import com.nagarro.userapp.entities.Users;
import com.nagarro.userapp.model.User;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SortByNameStrategy implements UserSortStrategy{

    @Override
    public List<Users> sort(List<Users> userList, String sortOrder) {
        Predicate<Users> sortingPredicate = user -> sortOrder.equalsIgnoreCase("even") == (user.getName().length() % 2 != 0);

        return userList.stream()
                .collect(Collectors.partitioningBy(sortingPredicate))
                .values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
//        return null;
    }

//    private static Predicate<Users> getSortingPredicate(String sortType, String sortOrder) {
//        sortType = sortType.toUpperCase();
//        switch (sortType) {
//            case "AGE":
//                return user -> sortOrder.equalsIgnoreCase("even") == (user.getAge() % 2 != 0);
//            case "NAME":
//                return user -> sortOrder.equalsIgnoreCase("even") == (user.getName().length() % 2 != 0);
//            default:
//                throw new IllegalArgumentException("Unsupported sortType: " + sortType);
//        }
//    }
}
