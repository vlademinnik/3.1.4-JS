package com.example.myboot.dao;





import com.example.myboot.model.User;

import java.util.List;

public interface UserDao {

    void save(User user);

    void deleteUser(long id);

    void editUser(User user, long id);

    User getUserByName(String name);

    List<User> getAllUsers();

    User getUserById(long id);
}
