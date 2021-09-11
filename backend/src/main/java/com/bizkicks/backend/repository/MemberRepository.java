package com.bizkicks.backend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bizkicks.backend.entity.Member;

import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    public Member findById(Long memberId){
        String selectMemberQuery = "SELECT u FROM Member u WHERE u.id = :member_id";
        List<Member> members = em.createQuery(selectMemberQuery, Member.class)
                                                    .setParameter("member_id", memberId)
                                                    .getResultList();
        if(members.isEmpty()) return null;
        else return members.get(0);
    }
    
}
