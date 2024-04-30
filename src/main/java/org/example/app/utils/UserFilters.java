package org.example.app.utils;

import org.example.app.db_connect.DbConnectInit;
import org.example.app.entity.User;

import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Logger;

public class UserFilters {
    private static final Logger UserFiltersLogger = Logger.getLogger(DbConnectInit.class.getName());
    public static List<User> filterUsers(List<User> users, List<String> excludeColumns) {
        List<User> filteredUsers = new ArrayList<>();
        for (User originalUser : users) {
            User filteredUser = cloneWithFilteredFields(originalUser, excludeColumns);
            filteredUsers.add(filteredUser);
        }
        return filteredUsers;
    }

    private static User cloneWithFilteredFields(User user, List<String> excludeColumns) {
        User clonedUser = new User();
        Field[] fields = User.class.getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();

                if (excludeColumns.contains(fieldName)) {
                    if ("id".equals(fieldName)) {
                        field.set(clonedUser, -1L);
                    } else {
                        field.set(clonedUser, "");
                    }
                } else {
                    field.set(clonedUser, field.get(user));
                }
            }
        } catch (IllegalAccessException e) {
            UserFiltersLogger.severe("Error cloning user: " + e.getMessage());
            throw new RuntimeException("Error cloning user", e);
        }

        return clonedUser;
    }
}
