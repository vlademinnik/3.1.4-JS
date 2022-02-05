package com.example.myboot.controller;


import com.example.myboot.model.Role;
import com.example.myboot.model.User;
import com.example.myboot.service.RoleService;
import com.example.myboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminRestController {
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public AdminRestController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok().body(roleService.getAllRole());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>>getAllUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }


    @GetMapping("/user")
    public ResponseEntity<User> getUserPages(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/create")
    public ResponseEntity<?> newUser (@RequestBody User user){
        userService.save(getUsersRole(user));
        System.out.println(user);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping ("/user/{id}")
    public ResponseEntity<User> getUserById (@PathVariable Long id){
        final User user = userService.getUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } //получение юзера по ID

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") int id, @RequestBody User user) {
       userService.editUser(getUsersRole(user), id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    } //редачим юзера

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") int id) {
        userService.deleteUser(id);
    } //удаляем юзера (оставил только Лонг)

    private User getUsersRole(User user) {
        List<Role> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(roleService.getRoleByName(role.getRole()));
        }
        user.setRoles(roles);
        return user;
    }
}

