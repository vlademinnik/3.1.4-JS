package com.example.myboot.dao;


import com.example.myboot.model.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleByName(String name);

    List<Role> getAllRole();

    void addRole(Role role);
}
