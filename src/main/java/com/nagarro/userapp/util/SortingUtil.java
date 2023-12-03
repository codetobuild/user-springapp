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


//        Predicate<Users> sortingPredicate = getSortingPredicate(sortType, sortOrder);
//        return users.stream()
//                .collect(Collectors.partitioningBy(sortingPredicate))
//                .values()
//                .stream()
//                .flatMap(List::stream)
//                .collect(Collectors.toList());
//    }

//    public static Predicate<Users> getSortingPredicate(String sortType, String sortOrder) {
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
//}
