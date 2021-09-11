package com.bizkicks.backend;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.bizkicks.backend.entity.Member;
import com.bizkicks.backend.repository.MemberRepository;

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
                        .type("manager")
                        .build();
        em.persist(this.member);
    }

    @Test
    void find_by_memberid(){
        // when
        Member selectedUser = userRepository.findById(member.getId());

        // then
        Assertions.assertThat(selectedUser.getMemberId()).isEqualTo(member.getMemberId());
        Assertions.assertThat(selectedUser.getPassword()).isEqualTo(member.getPassword());
        Assertions.assertThat(selectedUser.getType()).isEqualTo(member.getType());
    }
}