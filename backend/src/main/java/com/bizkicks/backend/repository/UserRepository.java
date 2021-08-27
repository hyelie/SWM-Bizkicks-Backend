package com.bizkicks.backend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bizkicks.backend.entity.User;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    public User findById(Integer userId){
        String selectUserQuery = "SELECT u FROM User u WHERE u.id = :user_id";
        List<User> users = em.createQuery(selectUserQuery, User.class)
                                                    .setParameter("user_id", userId)
                                                    .getResultList();
        if(users.isEmpty()) return null;
        else return users.get(0);
    }
    
}
