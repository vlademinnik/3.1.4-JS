package com.example.myboot.service;


import com.example.myboot.model.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String name);

    List<Role> getAllRole();

    void addRole(Role role);

}
