package com.example.myboot.controller;

import com.example.myboot.model.Role;
import com.example.myboot.model.User;
import com.example.myboot.service.RoleService;
import com.example.myboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String printAllUsers(Model model) {
        model.addAttribute("admin",  SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("users", userService.getAllUsers());
        return "users/administrator";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/new_user";
    }

    @PostMapping()
    public String postNewUser(@ModelAttribute("user") User user,
                              @RequestParam(required = false, name = "ADMIN") String ADMIN, Model model,  Authentication authentication,
                              @RequestParam(required = false, name = "USER") String USER) {
        Set<Role> sr = new HashSet<>();
        user.setRoles(sr);
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String upDate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/edit";
    }

    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable("id") Long id,
                             @RequestParam("role") String[] role) {
        Set<Role> roles = new HashSet<>();
        for (String stringRoles : role) {
            roles.add(roleService.getRoleByName(stringRoles));
        }
        user.setRoles(roles);
        userService.editUser(user, id);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(User user, @PathVariable("id") Long id) {
        userService.deleteUser(user, id);
        return "redirect:/admin";
    }
}
