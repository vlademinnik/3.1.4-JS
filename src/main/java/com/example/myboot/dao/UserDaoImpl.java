package com.example.myboot.dao;

import com.example.myboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(User user, long id) {
        User userDelete = getUserById(id);
        entityManager.remove(userDelete);
    }

    @Override
    public void editUser(User user, long id) {
        User userEdit = getUserById(id);
        user.setId(id);
//        userEdit.setUserName(user.getName());
        userEdit.setFirstName(user.getFirstName());
        userEdit.setLastName(user.getLastName());
        userEdit.setEmail(user.getEmail());
        userEdit.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.merge(userEdit);
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getUserByName(String email) {
        Query query = entityManager.createQuery("from User where email =?1");
        query.setParameter(1, email);
        return (User) query.getSingleResult();
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }
}
