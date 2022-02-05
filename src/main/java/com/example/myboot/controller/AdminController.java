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

import java.util.ArrayList;
import java.util.List;


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
        return "admin/infoAboutUser";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("admin", user);
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping()
    public String postNewUser(@ModelAttribute("user") User user,
                              @RequestParam("role") String[] role){
        List<Role> sr = new ArrayList<>();
        if (role != null) {
            for (String roles : role) {
                sr.add(roleService.getRoleByName(roles));
            }
            user.setRoles(sr);
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String upDate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/edit";
    }

    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable("id") Long id,
                             @RequestParam(value = "role", required = false) String[] role) {
        List<Role> roles = new ArrayList<>();
        for (String stringRoles : role) {
            roles.add(roleService.getRoleByName(stringRoles));
        }
        user.setRoles(roles);
        userService.editUser(user, id);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/info")
    public String adminInfo(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("admin", userService.getUserByName(email));
        return "admin/adminInfo";
    }
}
