package com.example.myboot.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Array;
import java.util.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn
            (name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles; //также поменял на List

    public User() {
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public List<Role> getRoles() {
        return roles;
    }


    public String getRoleRole() {
        String roleName = "";
        for (Role role : roles) {
            roleName += role.getRole();
        }
        return roleName;
    }

    public void setRoles(List<Role> roles) {
        this.roles = new ArrayList<>();
        for (Role role : roles) {
            if (role.getRole().contains("ROLE_ADMIN")) {
                this.roles.add(new Role("ROLE_ADMIN"));
            }
            if (role.getRole().contains("ROLE_USER")) {
                this.roles.add(new Role("ROLE_USER"));
            }
        }
    }
}