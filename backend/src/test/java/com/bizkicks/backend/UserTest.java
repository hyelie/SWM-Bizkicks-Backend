package com.bizkicks.backend;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.entity.UserRole;
import com.bizkicks.backend.auth.repository.MemberRepository;

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
    @Autowired MemberRepository userRepository;

    Member member;

    @BeforeEach
    void init(){
        this.member = Member.builder()
                        .memberId("userId")
                        .password("password")
                        .userRole(UserRole.ROLE_MANAGER)
                        .build();
        em.persist(this.member);
    }

    @Test
    void find_by_memberid(){
        // when
        Member selectedUser = userRepository.findById(member.getId()).get();

        // then
        Assertions.assertThat(selectedUser.getMemberId()).isEqualTo(member.getMemberId());
        Assertions.assertThat(selectedUser.getPassword()).isEqualTo(member.getPassword());
        Assertions.assertThat(selectedUser.getUserRole()).isEqualTo(member.getUserRole());
    }
}