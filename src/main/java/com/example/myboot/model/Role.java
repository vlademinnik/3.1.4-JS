package com.example.myboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "role")
    private String role;


    @ManyToMany(mappedBy = "roles")

    @JsonIgnore
    private List<User> usersList; // поменял Set на List + поставил аннотацию от бесконечной рекурскии

    public Role() {
    }

    @Override
    public String getAuthority() {
        return getRole();
    }

    @Override
    public String toString() {
        return role;
    }


    public Role(String role) {
        if (role.contains("ADMIN")) {
            this.id = 2L;
        } else if (role.contains("USER")) {
            this.id = 1L;
        }
        this.role = role;
    }
}