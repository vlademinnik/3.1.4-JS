package com.example.myboot.service;



import com.example.myboot.model.User;

import java.util.List;

public interface UserService {
    void save(User user);

    void deleteUser(User user, long id);

    void editUser(User user, long id);

    User getUserByName(String name);

    List<User> getAllUsers();

    User getUserById(long id);
}