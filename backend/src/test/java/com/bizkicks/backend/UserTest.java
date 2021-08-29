package com.bizkicks.backend;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.bizkicks.backend.entity.User;
import com.bizkicks.backend.repository.UserRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.NoArgsConstructor;

@SpringBootTest
@Transactional
@NoArgsConstructor
class UserRepositoryTest {
    @PersistenceContext EntityManager em;
    @Autowired UserRepository userRepository;

    User user;

    @BeforeEach
    void init(){
        this.user = User.builder()
                        .userId("userId")
                        .password("password")
                        .type("manager")
                        .build();
        em.persist(this.user);
    }

    @Test
    void find_by_userid(){
        // when
        User selectedUser = userRepository.findById(user.getId());

        // then
        Assertions.assertThat(selectedUser.getUserId()).isEqualTo(user.getUserId());
        Assertions.assertThat(selectedUser.getPassword()).isEqualTo(user.getPassword());
        Assertions.assertThat(selectedUser.getType()).isEqualTo(user.getType());
    }
}