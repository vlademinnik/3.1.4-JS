package com.example.myboot.controller;

import com.example.myboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String getUser(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("user", userService.getUserByName(principal.getName()));
        return "user/userInfo";
    }
}


