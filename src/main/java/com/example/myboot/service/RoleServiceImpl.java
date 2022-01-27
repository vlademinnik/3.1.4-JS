package com.example.myboot.service;

import com.example.myboot.dao.RoleDao;
import com.example.myboot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }
}
